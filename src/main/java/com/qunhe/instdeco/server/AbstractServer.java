package com.qunhe.instdeco.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author shengxun
 */
public abstract class AbstractServer implements Runnable {

    protected EventLoopGroup mBossGroup;

    protected EventLoopGroup mWorkerGroup;

    protected int mPort;

    protected ChannelInitializer<SocketChannel> mChannelInitializer;

    protected Thread mThread;

    public AbstractServer() {
        mThread = new Thread(this);
    }

    public void start() {
        mThread.start();
    }


    protected void defaultInitial() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);

            b.group(mBossGroup, mWorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(mChannelInitializer);
            System.out.println("start");
            Channel ch = b.bind(mPort).sync().channel();

            System.err.println("Open your web browser and navigate to " +
                    ("http") + "://127.0.0.1:" + mPort + '/');

            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mBossGroup.shutdownGracefully();
            mWorkerGroup.shutdownGracefully();
        }
    }

}
