package com.example.bank;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 胡宇靖 on 2017/4/8 0008.
 */

public class HttpUtil {
    public static void sendHttpRequest(String address,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
