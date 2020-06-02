package com.zh.template.net.api.upload;

import com.zh.template.net.use.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface UploadApi {
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
