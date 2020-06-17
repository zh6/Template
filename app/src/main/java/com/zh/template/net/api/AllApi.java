package com.zh.template.net.api;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AllApi {
    /**
     * 登录
     * @param map
     * @return
     */
    @GET("login/login")
    Observable<JSONObject> Login(Map<String, String> map);
}
