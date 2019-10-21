package com.zh.template.network;

import com.zh.template.module.main.entity.AddressEntity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface AllAPI {
    /**
     * 登录
     *
     * @param loginName 登录名
     * @param password  密码
     * @return
     */
    @GET("login/login")
    Observable<Object> Login(@Query("loginName") String loginName, @Query("password") String password, @Query("systemId") String systemId, @Query("nac") String nac, @Query("permisson") String permisson);

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @return
     */
    @GET("api/user/user/update")
    Observable<BaseResponse<String>> updatePwd(@Query("userId") String userId, @Query("password") String password);

    /**
     * 检查APP版本是否需要更新
     *
     * @param versionCode 当前版本号
     * @return
     */
    @GET("api/trade/deviceversion/getDistributionAppNewVersion")
    Observable<BaseResponse<Map<String, String>>> getDistributionAppNewVersion(@Query("versionCode") String versionCode);


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

    /**
     * 新增
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("api/trade/customer/customerRegist")
    Observable<BaseResponse<Object>> customerRegist(@FieldMap Map<String, String> map);


}
