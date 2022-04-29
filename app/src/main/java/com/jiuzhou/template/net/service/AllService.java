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

public class AllService {
    private final BaseApi allApi;

    private AllService() {
        allApi = RetrofitFactory.getInstance().create(BaseApi.class);
    }

    //静态内部类方式实现单例模式
    public static AllService getInstance() {
        return AllService.AllServiceHolder.INSTANCE;
    }

    private static class AllServiceHolder {
        private static final AllService INSTANCE = new AllService();
    }
}
