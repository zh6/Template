package com.zh.template.net.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.template.base.MyApplication;
import com.zh.template.utils.SharedPreferenceUtils;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Response response = null;
        // 新的请求,添加参数
        Request newRequest = addParam(oldRequest);
        response = chain.proceed(newRequest);
        String responseString = response.body().string();
        JSONObject jsonObject = JSON.parseObject(responseString);
        int state = jsonObject.getIntValue("state");
        if (state == 97) {
            // token失效，重新执行请求
            getNewToken();
            Request newTokenRequest = addParam(oldRequest);
            response = chain.proceed(newTokenRequest);
        } else {
            // 这里值得注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
            response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseString)).build();
        }
        return response;
    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {
        String token = SharedPreferenceUtils.getToken(MyApplication.getAppContext());
        //无token和登录接口不添加
        if (token.isEmpty() || oldRequest.url().toString().contains("login")) {
            return oldRequest;
        }
        HttpUrl.Builder url = oldRequest.url()
                .newBuilder()
                .setEncodedQueryParameter("token", token);
        Request newRequest = oldRequest.newBuilder()
//                .method(oldRequest.method(), oldRequest.body())
                .url(url.build())
                .build();

        return newRequest;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private void getNewToken() throws IOException {
        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
//        RetrofitService.getInstance().Login(SharedPreferenceUtils.getUserInfo(MyApplication.getAppContext()).get(0), SharedPreferenceUtils.getUserInfo(MyApplication.getAppContext()).get(1))
//                .doOnNext(res -> {
//                    JSONObject obj = JSON.parseObject(JSON.toJSONString(res));
//                    if (obj.getIntValue("state") == 0) {  //表示登录成功
//                        SharedPreferenceUtils.saveToken(MyApplication.getAppContext(), obj.getString("token"));
//                    }
//                }).subscribe();
    }

}
