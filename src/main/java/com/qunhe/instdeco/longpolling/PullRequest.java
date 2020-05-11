package com.qunhe.instdeco.longpolling;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;

/**
 * @author shengxun
 */
public class PullRequest {

    private final ChannelHandlerContext mCtx;

    private final HttpObject mMessage;


    public PullRequest(final ChannelHandlerContext ctx, final HttpObject msg) {
        this.mCtx = ctx;
        this.mMessage = msg;
    }

    public ChannelHandlerContext getCtx() {
        return mCtx;
    }

    public HttpObject getMessage() {
        return mMessage;
    }
}
