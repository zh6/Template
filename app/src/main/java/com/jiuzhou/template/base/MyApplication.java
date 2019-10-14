package com.jiuzhou.template.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.jiuzhou.template.utils.SharedPreferenceUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.jiuzhou.template.BuildConfig;
import com.jiuzhou.template.utils.LogUtils;
import com.jiuzhou.template.utils.ToastUtils;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.plugins.RxJavaPlugins;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * 自定义的Application，做一些初始化配置
 */
public class MyApplication extends Application {
    private List<Activity> oList;//用于存放所有启动的Activity的集合
    public static String cacheDir = "";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        oList = new ArrayList<Activity>();
        //初始化日志工具
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        ToastUtils.register(this);
        //LeakCanary检测OOM
        LeakCanary.install(this);
        //开启替换baseurl高级模式
        RetrofitUrlManager.getInstance().startAdvancedModel(SharedPreferenceUtils.getIp(this));
        //初始化日志输出工具
        //CrashHandler.init(new CrashHandler(getApplicationContext()));
        //如果存在SD卡则将缓存写入SD卡,否则写入手机内存
        if (getApplicationContext().getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();

        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
        //处理异常
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (throwable != null) {
                LogUtils.e(throwable.toString());
            } else {
                LogUtils.e("call onError but exception is null");
            }
        });
    }

    //获取上下文
    public static Context getAppContext() {
        return context.getApplicationContext();
    }

    private boolean isExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    public static String getAppCacheDir() {
        return cacheDir;
    }
}
