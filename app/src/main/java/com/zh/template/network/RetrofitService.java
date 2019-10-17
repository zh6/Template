package com.zh.template.network;

import android.support.annotation.NonNull;

import com.zh.template.base.MyApplication;
import com.zh.template.module.main.entity.AddressEntity;
import com.zh.template.utils.LogUtils;
import com.zh.template.utils.NetUtils;
import com.zh.template.utils.RxUtils;
import com.zh.template.utils.SharedPreferenceUtils;
import com.zh.template.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * <Pre>
 * 网络请求引擎类
 * </Pre>
 */
public class RetrofitService {
    //设缓存有效期为两天
    protected static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private volatile static OkHttpClient mOkHttpClient;
    private volatile static AllAPI mAPI = null;

    private RetrofitService() {
    }

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
                    .addConverterFactory(LenientGsonConverterFactory.create())
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
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            File cacheFile = new File(MyApplication.getAppContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            // 云端响应头拦截器，用来配置缓存策略
            Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!NetUtils.isConnected(MyApplication.getAppContext())) {
                        request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                        Logger.e("no network");
                    }
                    Response originalResponse = chain.proceed(request);
                    if (NetUtils.isConnected(MyApplication.getAppContext())) {
                        //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control", cacheControl)
                                .removeHeader("Pragma").build();
                    } else {
                        return originalResponse.newBuilder().header("Cache-Control",
                                "public, only-if-cached," + CACHE_STALE_SEC)
                                .removeHeader("Pragma").build();
                    }
                }
            };
            //okhttp 3
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache)
                    .addNetworkInterceptor(rewriteCacheControlInterceptor)
                    .addInterceptor(rewriteCacheControlInterceptor)
                    .addInterceptor(logInterceptor)
                    .addInterceptor(new TokenInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);
            mOkHttpClient = RetrofitUrlManager.getInstance().with(builder).build();

        }
    }

    /**
     * 根据网络状况获取缓存的策略
     *
     * @returna
     */
    @NonNull
    public static String getCacheControl() {
        return NetUtils.isConnected(MyApplication.getAppContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }
    /***********************************************************************************************************************/
    /**********************************************封装所有请求接口*********************************************************/
    /***********************************************************************************************************************/
    public Observable<Object> Login(String name, String pwd) {
        return createAPI().Login(name, pwd)
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
