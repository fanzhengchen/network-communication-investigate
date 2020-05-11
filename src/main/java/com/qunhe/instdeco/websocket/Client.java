/*
 * Client.java
 * Copyright 2020 Qunhe Tech, all rights reserved.
 * Qunhe PROPRIETARY/CONFIDENTIAL, any form of usage is subject to approval.
 */

package com.qunhe.instdeco.websocket;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author shengxun
 */
public class Client {

    public static void main(String[] args) {
        final OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:7000/websocket")
                .build();
        okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NotNull final WebSocket webSocket,
                    @NotNull final Response response) {
                System.out.println("open");
                webSocket.send("test websocket");
            }

            @Override
            public void onMessage(@NotNull final WebSocket webSocket, @NotNull final String text) {
                System.out.println("on message " + text);
                LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
                webSocket.send("send websocket");
            }
        });


    }
}
