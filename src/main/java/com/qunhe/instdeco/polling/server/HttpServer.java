package com.qunhe.instdeco.polling.server;

import com.qunhe.instdeco.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author shengxun
 */
public class HttpServer extends AbstractServer {

    public HttpServer() {

    }

    public static class HttpServerBuilder {

        HttpServer mServer;

        public HttpServerBuilder() {
            mServer = new HttpServer();
        }

        public HttpServerBuilder bossGroup(final EventLoopGroup bossGroup) {
            mServer.bossGroup = bossGroup;
            return this;
        }

        public HttpServerBuilder workerGroup(final EventLoopGroup workerGroup) {
            mServer.workerGroup = workerGroup;
            return this;
        }

        public HttpServerBuilder port(final int port) {
            mServer.port = port;
            return this;
        }

        public HttpServer build() {
            return mServer;
        }
    }

    @Override
    public void run() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer());
            System.out.println("start");
            Channel ch = b.bind(port).sync().channel();

            System.err.println("Open your web browser and navigate to " +
                    ("http") + "://127.0.0.1:" + port + '/');

            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
