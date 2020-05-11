package com.qunhe.instdeco.longpolling;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;

/**
 * @author shengxun
 */
public class PullRequestProcessor {

    private static final byte[] CONTENT = "long polling request".getBytes();


    private BlockingQueue<PullRequest> queue = new LinkedBlockingQueue<>();

    private final Thread thread;

    public PullRequestProcessor() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    PullRequest request = queue.poll();
                    if (request != null) {
                        handle(request, 10, TimeUnit.SECONDS);
                    }
                }
            }
        });
        thread.start();
    }

    public void addRequest(PullRequest request) {
        queue.offer(request);
    }

    public void handle(PullRequest request, long waitForMillis, TimeUnit timeUnit) {
        LockSupport.parkNanos(timeUnit.toNanos(waitForMillis));
        if (!isHttpRequest(request.getMessage())) {
            return;
        }
        HttpRequest req = (HttpRequest) request.getMessage();
        final ChannelHandlerContext ctx = request.getCtx();

        boolean keepAlive = HttpUtil.isKeepAlive(req);
        FullHttpResponse response = new DefaultFullHttpResponse(
                req.protocolVersion(), HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(CONTENT));
        response.headers()
                .set(CONTENT_TYPE, TEXT_PLAIN)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (keepAlive) {
            if (!req.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            // Tell the client we're going to close the connection.
            response.headers().set(CONNECTION, CLOSE);
        }

        ChannelFuture f = ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    System.out.println(channelFuture.channel().remoteAddress() + " failed");
                } else {
                    System.out.println("success");
                }
            }
        });

        if (!keepAlive) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private boolean isHttpRequest(HttpObject msg) {
        return (msg instanceof HttpRequest);
    }
}
