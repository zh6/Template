package com.zh.template.network;

import android.text.TextUtils;

import com.zh.template.base.MyApplication;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.network.interceptor.TokenInterceptor;
import com.zh.template.utils.LogUtils;
import com.zh.template.utils.NetUtils;
import com.zh.template.utils.RxUtils;
import com.zh.template.utils.SharedPreferenceUtils;
import com.zh.template.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 网络请求引擎类
 */
public class RetrofitService {
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private volatile static OkHttpClient mOkHttpClient;
    private volatile static AllAPI mAPI = null;
    private volatile static RetrofitService instance = null;

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                if (instance == null) {
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }

    /**
     * 创建api
     */
    public static AllAPI createAPI() {
        initOkHttpClient();
        if (mAPI == null) {
            mAPI = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(SharedPreferenceUtils.getIp(MyApplication.getAppContext()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(AllAPI.class);
        }
        return mAPI;
    }
    private static Consumer<Throwable> disposeFailureInfo(Throwable t) {
        return throwable -> {
            if (t.toString().contains("GaiException") || t.toString().contains("SocketTimeoutException") ||
                    t.toString().contains("UnknownHostException")) {
                ToastUtils.showShort("网络问题");
            } else if (t.toString().contains("API没有")) {
                ToastUtils.showShort("错误: " + t.getMessage());
            }
            LogUtils.w(t.getMessage());
        };
    }

    // 配置OkHttpClient
    private static void initOkHttpClient() {
        if (mOkHttpClient == null) {
            File cacheFile = new File(MyApplication.getAppContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            // 有网时候的缓存
            Interceptor NetCacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    int onlineCacheTime = 30;//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
                    String cacheControl = request.cacheControl().toString();
                    if (TextUtils.isEmpty(cacheControl)) {
                        cacheControl = "public, max-age=" + onlineCacheTime;
                    }
                    return response.newBuilder()
                            .header("Cache-Control", "public,"+cacheControl)
                            .removeHeader("Pragma")
                            .build();
                }
            };
            //没有网时候的缓存
            Interceptor OfflineCacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetUtils.isConnected(MyApplication.getAppContext())) {
                        int offlineCacheTime = 60;//离线的时候的缓存的过期时间
                        request = request.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + offlineCacheTime)
                                .build();
                    }
                    return chain.proceed(request);
                }
            };
            //日志
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //okhttp 3
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .addNetworkInterceptor(NetCacheInterceptor)
                    .addInterceptor(OfflineCacheInterceptor)
                    .cache(cache)
                    .addInterceptor(logInterceptor)
                    .addNetworkInterceptor(new TokenInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
            mOkHttpClient = RetrofitUrlManager.getInstance().with(builder).build();

        }
    }
    /***********************************************************************************************************************/
    /**********************************************封装所有请求接口*********************************************************/
    /***********************************************************************************************************************/
    public Observable<Object> Login(String name, String pwd) {
        return createAPI().Login(name, pwd, "8", "y", "no")
                .doOnError(RetrofitService::disposeFailureInfo)
                .compose(RxUtils.io());
    }

    public Observable<String> updatePwd(String userId, String password) {
        return createAPI().updatePwd(userId, password)
                .flatMap(res -> {
                    if (res.state == 0) {
                        return Observable.just(res.msg);
                    } else {
                        return Observable.error(new RuntimeException(res.msg));
                    }
                })
                .doOnError(RetrofitService::disposeFailureInfo)
                .compose(RxUtils.io());
    }

    public Observable<Map<String, String>> getDistributionAppNewVersion(String versionCode) {
        return createAPI().getDistributionAppNewVersion(versionCode)
                .flatMap(res -> {
                    if (res.state == 0) {
                        return Observable.just(res.aaData);
                    } else {
                        return Observable.error(new RuntimeException(res.msg));
                    }
                })
                .doOnError(RetrofitService::disposeFailureInfo)
                .compose(RxUtils.io());
    }


    public Observable<List<AddressEntity>> getAreaList(String parentAreaCode, String level) {
        return createAPI().getAreaList(CACHE_CONTROL_NETWORK, parentAreaCode, level)
                .flatMap(response -> {
                    if (response.state == 0) {
                        return Observable.just(response.aaData);
                    } else {
                        return Observable.error(new RuntimeException(response.msg));
                    }
                })
                .doOnError(RetrofitService::disposeFailureInfo)
                .compose(RxUtils.io());
    }


    public Observable<Map> uploadImages(Map<String, RequestBody> map) {
        return createAPI().uploadImages(map)
                .flatMap(response -> {
                    if (response.state == 0) {
                        return Observable.just(response.aaData);
                    } else {
                        return Observable.error(new RuntimeException(response.msg));
                    }
                })
                .doOnError(RetrofitService::disposeFailureInfo)
                .compose(RxUtils.io());
    }


    public Observable<Object> customerRegist(Map<String, String> map) {
        return createAPI().customerRegist(map)
                .flatMap(response -> {
                    if (response.state == 0) {
                        return Observable.just(response.msg);
                    } else {
                        return Observable.error(new RuntimeException(response.msg));
                    }
                })
                .doOnError(RetrofitService::disposeFailureInfo)
                .compose(RxUtils.io());
    }
}
