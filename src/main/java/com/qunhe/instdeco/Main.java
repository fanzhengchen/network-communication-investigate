package com.qunhe.instdeco;

import com.qunhe.instdeco.polling.server.HttpServer;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author shengxun
 */
public class Main {

    public static void main(String[] args) {
        HttpServer server = new HttpServer.HttpServerBuilder()
                .port(7000)
                .bossGroup(new NioEventLoopGroup())
                .workerGroup(new NioEventLoopGroup(1))
                .build();
        server.start();
    }
}
