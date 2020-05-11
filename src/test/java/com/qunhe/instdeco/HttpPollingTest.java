package com.qunhe.instdeco;

import com.qunhe.instdeco.http.HttpClient;
import com.qunhe.instdeco.polling.HttpPollingServer;
import io.netty.channel.nio.NioEventLoopGroup;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author shengxun
 */
public class HttpPollingTest {

    final int port = 7000;

    final String host = "localhost";

    @Test
    public void testPolling() {
        final Thread cur = Thread.currentThread();

        HttpPollingServer server = new HttpPollingServer.HttpServerBuilder()
                .port(port)
                .bossGroup(new NioEventLoopGroup())
                .workerGroup(new NioEventLoopGroup(1))
                .build();
        server.start();

        /**
         * 暂停2s 等服务启动
         */
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));

        final HttpClient httpClient = new HttpClient();
        final String url = "http://" + host + ":" + port;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int cnt = 0;
                for (; ; ) {
                    String result = httpClient.get(url);
                    long time = System.currentTimeMillis();
                    System.out.println(result + " " + (++cnt) + " " + time);
                    /**
                     * 间隔3s访问一次
                     */
                    LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));
                }
            }
        });
        thread.start();


        LockSupport.park();
    }
}
