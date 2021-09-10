package com.zh.template.base;

import android.app.Activity;
import android.app.Application;
import android.os.Process;
import android.text.TextUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zh.template.R;
import com.zh.template.utils.LogUtil;
import com.zh.template.utils.MultiLanguageUtil;
import com.zh.template.utils.SPUtil;
import com.zh.template.utils.ToastUtil;

import java.util.Stack;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * 自定义的Application，做一些初始化配置
 */
public class MyApplication extends Application {
    private static Stack<Activity> sActivityStack;
    private static MyApplication appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        //多语言设置
        registerActivityLifecycleCallbacks(MultiLanguageUtil.callbacks);
        //如果ip为空，设置默认请求地址
        if (TextUtils.isEmpty(SPUtil.SYSTEM.getString("ip", ""))) {
            //测试   http://10.2.5.37:9010/
            //演示   http://125.69.76.108:9011/
            //开发   http://10.2.15.82:8009/
            SPUtil.SYSTEM.putString("ip", "http://10.2.5.37:9010/").commit();
        }
        //初始化activity栈
        sActivityStack = new Stack<>();
        //日志工具
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        ToastUtil.register(this);
        //LeakCanary检测OOM
//        LeakCanary.install(this);
        //异常捕捉
        //CrashHandler.init(new CrashHandler(getApplicationContext()));
        //RXJava处理异常
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (throwable != null) {
                LogUtil.e(throwable.toString());
            } else {
                LogUtil.e("call onError but exception is null");
            }
        });
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.background, android.R.color.black);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context).setDrawableSize(20));

    }

    //获取上下文
    public static MyApplication getAppContext() {
        return appContext;
    }

    public void addActivity(Activity activity) {
        sActivityStack.add(activity);
    }

    public void finishActivity(Activity activity) {
        sActivityStack.remove(activity);
        activity.finish();
    }

    public void finishAllActivity() {
        for (int i = 0; i < sActivityStack.size(); i++) {
            if (sActivityStack.get(i) != null) {
                sActivityStack.get(i).finish();
            }
        }
    }

    public void AppExit() {
        try {
            finishAllActivity();
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
