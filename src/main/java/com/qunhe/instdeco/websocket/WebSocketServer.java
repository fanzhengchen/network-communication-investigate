package com.qunhe.instdeco.websocket;

import com.qunhe.instdeco.polling.HttpPollingServer;
import com.qunhe.instdeco.server.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author shengxun
 */
public class WebSocketServer extends AbstractServer {

    public static class ServerBuilder {

        WebSocketServer mServer;

        public ServerBuilder() {
            mServer = new WebSocketServer();
        }

        public ServerBuilder bossGroup(final EventLoopGroup bossGroup) {
            mServer.mBossGroup = bossGroup;
            return this;
        }

        public ServerBuilder workerGroup(final EventLoopGroup workerGroup) {
            mServer.mWorkerGroup = workerGroup;
            return this;
        }

        public ServerBuilder port(final int port) {
            mServer.mPort = port;
            return this;
        }

        public ServerBuilder channelInitializer(final ChannelInitializer<SocketChannel> initializer) {
            mServer.mChannelInitializer = initializer;
            return this;
        }

        public WebSocketServer build() {
            return mServer;
        }
    }

    @Override
    public void run() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(mBossGroup, mWorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketInitializer())
                    .handler(new LoggingHandler(LogLevel.INFO));

            Channel channel = serverBootstrap.bind(mPort).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mBossGroup.shutdownGracefully();
            mWorkerGroup.shutdownGracefully();
        }
    }
}
