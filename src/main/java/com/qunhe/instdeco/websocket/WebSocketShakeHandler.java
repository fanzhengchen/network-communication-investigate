package com.qunhe.instdeco.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.CharsetUtil;

/**
 * @author shengxun
 */
public class WebSocketShakeHandler extends SimpleChannelInboundHandler<FullHttpRequest> {



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest req) throws Exception {
        if (!req.decoderResult().isSuccess()) {

        }

        if (req.method() != HttpMethod.GET) {

        }

        if (req.uri().equals("/websocket")) {

        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
            FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpUtil.setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}