package com.qunhe.instdeco.longpolling;

import com.qunhe.instdeco.polling.HttpPollingServer;
import com.qunhe.instdeco.server.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author shengxun
 */
public class HttpLongPollingServer extends AbstractServer {

    public static class HttpServerBuilder {

        HttpLongPollingServer mServer;

        public HttpServerBuilder() {
            mServer = new HttpLongPollingServer();
        }

        public HttpLongPollingServer.HttpServerBuilder bossGroup(final EventLoopGroup bossGroup) {
            mServer.mBossGroup = bossGroup;
            return this;
        }

        public HttpLongPollingServer.HttpServerBuilder workerGroup(
                final EventLoopGroup workerGroup) {
            mServer.mWorkerGroup = workerGroup;
            return this;
        }

        public HttpLongPollingServer.HttpServerBuilder port(final int port) {
            mServer.mPort = port;
            return this;
        }

        public HttpLongPollingServer.HttpServerBuilder channelInitializer(
                final ChannelInitializer<SocketChannel> initializer) {
            mServer.mChannelInitializer = initializer;
            return this;
        }

        public HttpLongPollingServer build() {
            return mServer;
        }
    }

    @Override
    public void run() {
        defaultInitial();
    }
}
