package com.zh.template.net.api;

import com.alibaba.fastjson.JSONObject;
import com.zh.template.entity.AddressEntity;
import com.zh.template.net.use.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AllApi {
    /**
     * 登录
     * @param map
     * @return
     */
    @GET("login/login")
    Observable<JSONObject> Login(Map<String, String> map);
    /**
     * 获取地址信息
     *
     * @param parentAreaCode
     * @param level
     * @return
     */
    @GET("api/trade/comArea/list")
    Observable<BaseResponse<List<AddressEntity>>> getAreaList(@Query("parentAreaCode") String parentAreaCode, @Query("level") String level);
}
