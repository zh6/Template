package com.jiuzhou.template.net.use;


import com.jiuzhou.template.common.Constants;
import com.jiuzhou.template.net.interceptor.HeaderInterceptor;
import com.jiuzhou.template.net.interceptor.LogInterceptor;
import com.jiuzhou.template.utils.SpUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.Nullable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private final Retrofit.Builder retrofit;
    private Retrofit build;

    private RetrofitFactory() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder()
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        OkHttpClient httpClient = httpClientBuilder.build();
        retrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        build = retrofit.baseUrl(SpUtils.SYSTEM.getStringDef(Constants.IP, Constants.API_URL)).build();
    }

    private static class RetrofitFactoryHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
    }

    public static final RetrofitFactory getInstance() {
        return RetrofitFactoryHolder.INSTANCE;
    }


    /**
     * 根据Api接口类生成Api实体
     *
     * @param clazz 传入的Api接口类
     * @return Api实体类
     */
    public <T> T create(Class<T> clazz) {
        checkNotNull(build, "BaseUrl not init,you should init first!");
        return build.create(clazz);
    }

    /**
     * 根据Api接口类生成Api实体
     *
     * @param baseUrl baseUrl
     * @param clazz   传入的Api接口类
     * @return Api实体类
     */
    public <T> T create(String baseUrl, Class<T> clazz) {
        return retrofit.baseUrl(baseUrl).build().create(clazz);
    }

    private <T> T checkNotNull(@Nullable T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
