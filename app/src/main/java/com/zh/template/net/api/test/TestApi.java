package com.zh.template.net.api.test;

import com.alibaba.fastjson.JSON;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.net.use.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TestApi {
    /**
     * 获取地址信息
     *
     * @param parentAreaCode
     * @param level
     * @return
     */
    @GET("api/trade/comArea/list")
    Observable<BaseResponse<List<AddressEntity>>> getAreaList(@Header("Cache-Control") String cacheControl, @Query("parentAreaCode") String parentAreaCode, @Query("level") String level);

    @FormUrlEncoded
    @POST("faceverify")
    Observable<JSON> faceverify(@Query("access_token") String token,@FieldMap Map<String, String> map);
}
