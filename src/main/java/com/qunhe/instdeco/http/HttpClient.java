package com.qunhe.instdeco.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author shengxun
 */
public class HttpClient {

    private OkHttpClient mOkHttpClient = new OkHttpClient();

    public HttpClient() {

    }

    public String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            String result = mOkHttpClient.newCall(request)
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
