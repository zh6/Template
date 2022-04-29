package com.jiuzhou.template.net.api;

import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.template.entity.AddressEntity;
import com.jiuzhou.template.entity.LoginRespEntity;
import com.jiuzhou.template.entity.UpFileEntity;
import com.jiuzhou.template.net.use.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BaseApi {

    @POST("api/user/user/updatePassword")
    Observable<BaseResponse<Object>> updatePassword(@Query("userId") Long userId, @Query("oldPassword") String oldPassword, @Query("passWord") String passWord);

    @POST("api/user/user/update")
    Observable<BaseResponse<Object>> userUpdate(@QueryMap Map<String, Object> map);

    /**
     * 获取地址信息
     *
     * @param parentAreaCode
     * @param level
     * @return
     */
    @POST("api/trade/comArea/list")
    Observable<BaseResponse<List<AddressEntity>>> getAreaList(@Query("parentAreaCode") String parentAreaCode, @Query("level") String level);


    /**
     * 系统图标标题配置
     *
     * @param dataType
     * @return
     */
    @POST("api/user/sysLogoConf/queryByParam")
    Observable<BaseResponse<JSONObject>> getSysLogo(@Query("dataType") String dataType, @Query("dataKey") String dataKey);


    /**
     * 获取数据字典
     *
     * @param dataType
     * @return
     */
    @POST("api/user/dataDictionary/getDataByType")
    Observable<BaseResponse<Map<String, Object>>> getDataByType(@Query("dataType") String dataType);

    /**
     * 登录
     *
     * @param map
     * @return
     */
    @POST("login/login")
    Observable<LoginRespEntity> Login(@QueryMap Map<String, String> map);

    /**
     * 上传文件
     *
     * @param maps
     * @param file
     * @return
     */
    @Multipart
    @POST("api/user/fileUpload/file/upload")
    Observable<UpFileEntity> upFile(@PartMap Map<String, RequestBody> maps,
                                    @Part MultipartBody.Part file);

}
