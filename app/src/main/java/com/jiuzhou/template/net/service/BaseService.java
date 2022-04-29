package com.jiuzhou.template.net.service;

import com.alibaba.fastjson.JSONObject;
import com.jiuzhou.template.entity.AddressEntity;
import com.jiuzhou.template.entity.LoginRespEntity;
import com.jiuzhou.template.entity.UpFileEntity;
import com.jiuzhou.template.net.api.BaseApi;
import com.jiuzhou.template.net.use.BaseResponse;
import com.jiuzhou.template.net.use.RetrofitFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BaseService {
    private final BaseApi allApi;

    private BaseService() {
        allApi = RetrofitFactory.getInstance().create(BaseApi.class);
    }

    //静态内部类方式实现单例模式
    public static BaseService getInstance() {
        return BaseService.BaseServiceHolder.INSTANCE;
    }

    private static class BaseServiceHolder {
        private static final BaseService INSTANCE = new BaseService();
    }

    public Observable<BaseResponse<Object>> updatePassword(Long userId, String oldPassword, String passWord) {
        return allApi.updatePassword(userId, oldPassword, passWord);
    }

    public Observable<BaseResponse<Object>> userUpdate(Map<String, Object> map) {
        return allApi.userUpdate(map);
    }

    public Observable<BaseResponse<List<AddressEntity>>> getAreaList(String parentAreaCode, String level) {
        return allApi.getAreaList(parentAreaCode, level);
    }

    public Observable<BaseResponse<JSONObject>> getSysLogo(String dataType, String dataKey) {
        return allApi.getSysLogo(dataType, dataKey);
    }

    public Observable<BaseResponse<Map<String, Object>>> getDataByType(String dataType) {
        return allApi.getDataByType(dataType);
    }

    public Observable<LoginRespEntity> Login(Map<String, String> map) {
        return allApi.Login(map);
    }

    public Observable<UpFileEntity> upFile(String fileName, String savePath, String contentType, File file) {
        Map<String, RequestBody> maps = new HashMap<>();
        maps.put("fileName", RequestBody.Companion.create(fileName, MediaType.Companion.parse("text/plain")));
        maps.put("savePath", RequestBody.Companion.create(savePath, MediaType.Companion.parse("text/plain")));
        maps.put("contentType", RequestBody.Companion.create(contentType, MediaType.Companion.parse("text/plain")));
        RequestBody imageBodyOne = RequestBody.Companion.create(file, MediaType.Companion.parse("multipart/form-data"));
        MultipartBody.Part imagePartOne = MultipartBody.Part.createFormData("file",
                file.getName(), imageBodyOne);
        return allApi.upFile(maps, imagePartOne);
    }
}
