package com.zh.template.net.service;

import com.alibaba.fastjson.JSONObject;
import com.zh.template.entity.AddressEntity;
import com.zh.template.net.api.AllApi;
import com.zh.template.net.use.BaseResponse;
import com.zh.template.net.use.RetrofitFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class AllService {
    private final AllApi allApi;

    private AllService() {
        allApi = RetrofitFactory.getInstance().create(AllApi.class);
    }

    //静态内部类方式实现单例模式
    public static AllService getInstance() {
        return AllService.AllServiceHolder.INSTANCE;
    }

    private static class AllServiceHolder {
        private static final AllService INSTANCE = new AllService();
    }

    public Observable<JSONObject> Login(Map<String, String> map) {
        return allApi.Login(map);
    }

    public Observable<BaseResponse<List<AddressEntity>>> getAreaList(String parentAreaCode, String level) {
        return allApi.getAreaList(parentAreaCode, level);
    }
}
