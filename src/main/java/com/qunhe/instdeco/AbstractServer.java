package com.qunhe.instdeco;

import com.qunhe.instdeco.polling.server.HttpServer;
import com.qunhe.instdeco.polling.server.HttpServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author shengxun
 */
public abstract class AbstractServer implements Runnable {

    protected EventLoopGroup bossGroup;

    protected EventLoopGroup workerGroup;

    protected int port;

    protected Thread mThread;

    public AbstractServer() {
        mThread = new Thread(this);
    }

    public void start() {
        mThread.start();
    }



}
