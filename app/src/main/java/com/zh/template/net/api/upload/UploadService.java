package com.zh.template.net.api.upload;

import com.zh.template.net.use.BaseResponse;
import com.zh.template.net.use.RetrofitFactory;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class UploadService {
    private final UploadApi mUploadApi;

    private UploadService() {
        String url = "http://fnw-api-nginx-fnw-test.topaas.enncloud.cn/";
        mUploadApi = RetrofitFactory.getInstance().create(url, UploadApi.class);
    }

    public static UploadService getInstance() {
        return UploadServiceHolder.S_INSTANCE;
    }

    private static class UploadServiceHolder {
        private static final UploadService S_INSTANCE = new UploadService();
    }

    public Observable<BaseResponse<Map>> uploadImages(Map<String, RequestBody> map) {
        return mUploadApi.uploadImages(map);
    }
}
