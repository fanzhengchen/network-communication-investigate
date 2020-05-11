package com.qunhe.instdeco;

import com.qunhe.instdeco.longpolling.HttpLongPollingInitializer;
import com.qunhe.instdeco.longpolling.HttpLongPollingServer;
import com.qunhe.instdeco.longpolling.PullRequestProcessor;
import com.qunhe.instdeco.polling.HttpPollingServer;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author shengxun
 */
public class Main {

    public static void main(String[] args) {
        new Main().longPolling();
    }

    public void run() {

    }

    public void polling(){
        HttpPollingServer server = new HttpPollingServer.HttpServerBuilder()
                .port(7000)
                .bossGroup(new NioEventLoopGroup())
                .workerGroup(new NioEventLoopGroup(1))
                .build();
        server.start();
    }

    public void longPolling(){
        HttpLongPollingServer server = new HttpLongPollingServer.HttpServerBuilder()
                .port(7000)
                .bossGroup(new NioEventLoopGroup())
                .workerGroup(new NioEventLoopGroup(1))
                .channelInitializer(new HttpLongPollingInitializer(new PullRequestProcessor()))
                .build();

        server.start();
    }
}
