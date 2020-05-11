package com.qunhe.instdeco.longpolling;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

/**
 * @author shengxun
 */
public class HttpLongPollingInitializer extends ChannelInitializer<SocketChannel> {

    private PullRequestProcessor mLongRequestProcessor;

    public HttpLongPollingInitializer(PullRequestProcessor longRequestProcessor) {
        mLongRequestProcessor = longRequestProcessor;
    }

    @Override
    protected void initChannel(final SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpLongPolingHandler(mLongRequestProcessor));
    }
}
