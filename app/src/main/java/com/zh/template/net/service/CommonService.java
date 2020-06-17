package com.zh.template.net.service;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.net.api.CommonApi;
import com.zh.template.net.use.BaseResponse;
import com.zh.template.net.use.RetrofitFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class CommonService {
    private final CommonApi commonApi;

    private CommonService() {
        commonApi = RetrofitFactory.getInstance().create(CommonApi.class);
    }

    //静态内部类方式实现单例模式
    public static CommonService getInstance() {
        return CommonService.CommonServiceHolder.INSTANCE;
    }

    private static class CommonServiceHolder {
        private static final CommonService INSTANCE = new CommonService();
    }
    public Observable<BaseResponse<List<AddressEntity>>> getAreaList(String parentAreaCode, String level) {
        return commonApi.getAreaList("max-age=0",parentAreaCode,level);
    }
    public Observable<BaseResponse<Map>> uploadImages(Map<String, RequestBody> map) {
        return commonApi.uploadImages(map);
    }
}
