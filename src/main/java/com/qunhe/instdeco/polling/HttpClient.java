package com.qunhe.instdeco.polling;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

/**
 * @author shengxun
 */
public class HttpClient {

    private OkHttpClient okHttpClient = new OkHttpClient();

    public HttpClient() {

    }

    public String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            String result = okHttpClient.newCall(request)
                    .execute()
                    .body()
                    .string();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
