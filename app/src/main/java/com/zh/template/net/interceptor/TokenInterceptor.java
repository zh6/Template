package com.zh.template.net.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zh.template.base.MyApplication;
import com.zh.template.net.service.AllService;
import com.zh.template.net.use.BaseObserver;
import com.zh.template.utils.SharedPreferenceUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        // 添加参数
        Request newRequest = addParam(oldRequest);
        //返回
        Response response = chain.proceed(newRequest);
        String responseStr = response.body().string();
        JSONObject jsonObject = JSON.parseObject(responseStr);
        int state = jsonObject.getIntValue("state");
        if (state == 97) {
            // token失效，重新执行请求
            getNewToken();
            Request newTokenRequest = addParam(oldRequest);
            response = chain.proceed(newTokenRequest);
        } else {
            // 这里值得注意。由于前面value.bytes()把响应流读完并关闭了，所以这里需要重新生成一个response，否则数据就无法正常解析了
            response = response.newBuilder().body(ResponseBody.create(response.body().contentType(), responseStr)).build();
        }
        return response;
    }

    /**
     * 添加公共参数
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {
        String token = SharedPreferenceUtils.getToken(MyApplication.getAppContext());
        //无token和登录接口不添加
        if (token.isEmpty() || oldRequest.url().toString().contains("login")) {
            return oldRequest;
        }
        Request newRequest = oldRequest.newBuilder().addHeader("Authorization",token).build();
        return newRequest;
    }

    /**
     * 登录接口获取最新的Token
     * @throws IOException
     */
    private void getNewToken() throws IOException {
        //重新调用登录接口 获取token
//        Map<String, String> map = new HashMap<>();
//        map.put("loginName", SharedPreferenceUtils.getUserInfo(MyApplication.getAppContext()).get(0));
//        map.put("password", SharedPreferenceUtils.getUserInfo(MyApplication.getAppContext()).get(1));
//        map.put("systemId", "2");
//        map.put("flag", "0");
//        map.put("authCode", "O0i9");
//        map.put("permisson", "no");
//        AllService.getInstance().Login(map).subscribe(new BaseObserver<JSONObject>() {
//            @Override
//            public void onSuccess(JSONObject response) {
//                if (response.getIntValue("state") == 0) {  //表示登录成功
//                    SharedPreferenceUtils.saveToken(MyApplication.getAppContext(), response.getString("token"));
//                }
//            }
//        });
    }

}
