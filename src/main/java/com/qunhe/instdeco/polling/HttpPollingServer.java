package com.qunhe.instdeco.polling;

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
public class HttpPollingServer extends AbstractServer {

    public HttpPollingServer() {

    }

    public static class HttpServerBuilder {

        HttpPollingServer mServer;

        public HttpServerBuilder() {
            mServer = new HttpPollingServer();
        }

        public HttpServerBuilder bossGroup(final EventLoopGroup bossGroup) {
            mServer.mBossGroup = bossGroup;
            return this;
        }

        public HttpServerBuilder workerGroup(final EventLoopGroup workerGroup) {
            mServer.mWorkerGroup = workerGroup;
            return this;
        }

        public HttpServerBuilder port(final int port) {
            mServer.mPort = port;
            return this;
        }

        public HttpServerBuilder channelInitializer(final ChannelInitializer<SocketChannel> initializer) {
            mServer.mChannelInitializer = initializer;
            return this;
        }

        public HttpPollingServer build() {
            return mServer;
        }
    }

    @Override
    public void run() {
       defaultInitial();
    }
}
