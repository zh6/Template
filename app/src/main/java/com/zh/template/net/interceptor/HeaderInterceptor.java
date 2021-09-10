package com.zh.template.net.interceptor;

import com.zh.template.utils.SPUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 添加参数
        Request newRequest = addHeader(oldRequest);
        Response response = chain.proceed(newRequest);
        return response;
    }

    /**
     * 添加公共参数
     *
     * @param request
     * @return
     */
    private Request addHeader(Request request) {
        String token = SPUtil.USER.getString("token", "");
        if (token.isEmpty() || String.valueOf(request.url()).contains("login")) {
            return request;
        }
        return request.newBuilder().addHeader("Authorization", token).build();
    }
}
