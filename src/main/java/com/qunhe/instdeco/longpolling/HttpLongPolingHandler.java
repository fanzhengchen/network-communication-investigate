package com.qunhe.instdeco.longpolling;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.CLOSE;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpHeaderValues.TEXT_PLAIN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * @author shengxun
 */
public class HttpLongPolingHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final byte[] CONTENT = "QunHe Long Polling Test".getBytes();

    private PullRequestProcessor mLongRequestProcessor;

    public HttpLongPolingHandler(PullRequestProcessor requestProcessor) {
        mLongRequestProcessor = requestProcessor;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final HttpObject msg)
            throws Exception {
        if (msg instanceof HttpRequest) {
            mLongRequestProcessor.addRequest(new PullRequest(ctx, msg));
        }
    }
}

