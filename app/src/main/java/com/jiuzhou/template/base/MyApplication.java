package com.jiuzhou.template.base;

import android.app.Activity;
import android.app.Application;
import android.os.Process;

import com.jiuzhou.template.R;
import com.jiuzhou.template.utils.LogUtils;
import com.jiuzhou.template.utils.MultiLanguageUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.tencent.mmkv.MMKV;

import java.util.Stack;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;


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
        //初始化mmkv
        String rootDir = MMKV.initialize(this);
        System.out.println("mmkv root: " + rootDir);
        //多语言设置
        registerActivityLifecycleCallbacks(MultiLanguageUtils.callbacks);
        //初始化activity栈
        sActivityStack = new Stack<>();
        //LeakCanary检测OOM
//        LeakCanary.install(this);
        //异常捕捉
        //CrashHandler.init(new CrashHandler(getApplicationContext()));
        //RXJava处理异常
        RxJavaPlugins.setErrorHandler(throwable -> {
            if (throwable != null) {
                LogUtils.e(throwable.toString());
            } else {
                LogUtils.e("call onError but exception is null");
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
