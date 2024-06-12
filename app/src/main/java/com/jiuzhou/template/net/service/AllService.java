package com.jiuzhou.template.net.service;

import com.jiuzhou.template.net.api.BaseApi;
import com.jiuzhou.template.net.use.RetrofitFactory;

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
