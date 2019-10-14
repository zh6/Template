package com.jiuzhou.template.utils;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 捕捉崩溃异常
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private final String TAG = CrashHandler.class.getSimpleName();

    private static Thread.UncaughtExceptionHandler defaultHandler = null;

    private Context context = null;

    public CrashHandler(Context context) {
        this.context = context;
    }

    /**
     * 初始化,设置该CrashHandler为程序的默认处理器
     */
    public static void init(CrashHandler crashHandler) {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogUtils.e(TAG, ex.toString());
        LogUtils.e(TAG, getCrashDeviceInfo());
        LogUtils.e(TAG, getCrashInfo(ex));
        // 调用系统错误机制
        defaultHandler.uncaughtException(thread, ex);
    }

    /**
     * 获取应用崩溃的详细信息
     *
     * @param ex
     * @return
     */
    public String getCrashInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.setStackTrace(ex.getStackTrace());
        ex.printStackTrace(printWriter);
        return writer.toString();
    }

    public String getCrashDeviceInfo() {
        String versionName = DeviceUtils.getVersionName(context);
        String model = android.os.Build.MODEL;
        String androidVersion = android.os.Build.VERSION.RELEASE;
        String manufacturer = android.os.Build.MANUFACTURER;
        return versionName + "  " +
                model + "  " +
                androidVersion + "  " +
                manufacturer;
    }
}
