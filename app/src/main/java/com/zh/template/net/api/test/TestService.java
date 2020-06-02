package com.zh.template.net.api.test;

import com.alibaba.fastjson.JSON;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.net.use.BaseResponse;
import com.zh.template.net.use.RetrofitFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class TestService {
    private final TestApi mTestApi;
//    String baseUrl="https://aip.baidubce.com/rest/2.0/face/v3/";
    private TestService() {
        mTestApi = RetrofitFactory.getInstance().create(TestApi.class);
    }

    public static TestService getInstance() {
        return TestServiceHolder.S_INSTANCE;
    }

    private static class TestServiceHolder {
        private static final TestService S_INSTANCE = new TestService();
    }

    public Observable<BaseResponse<List<AddressEntity>>> getAreaList(String parentAreaCode, String level) {
        return mTestApi.getAreaList("max-age=0",parentAreaCode,level);
    }
    public Observable<JSON> faceverify(Map<String,String> map) {
        return mTestApi.faceverify("test",map);
    }

}
