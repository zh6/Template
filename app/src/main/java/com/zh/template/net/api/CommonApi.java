package com.zh.template.net.api;


import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.net.use.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface CommonApi {
    /**
     * 获取地址信息
     *
     * @param parentAreaCode
     * @param level
     * @return
     */
    @GET("api/trade/comArea/list")
    Observable<BaseResponse<List<AddressEntity>>> getAreaList(@Header("Cache-Control") String cacheControl, @Query("parentAreaCode") String parentAreaCode, @Query("level") String level);
    /**
     * 上传图片
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("api/comm/fileUpload/file/imgUpload")
    Observable<BaseResponse<Map>> uploadImages(@PartMap Map<String, RequestBody> map);
}
