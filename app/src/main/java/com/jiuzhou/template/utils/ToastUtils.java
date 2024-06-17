package com.jiuzhou.template.utils;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import es.dmoral.toasty.Toasty;

public class ToastUtils {

    /**
     * 弹出成功消息
     *
     * @param text       需要显示的消息
     * @param isShowIcon 是否需要显示图标 默认显示
     */
    public static void toastSuccess(Context context, @NonNull String text, Boolean isShowIcon) {
        Toasty.success(context, text, Toast.LENGTH_SHORT, isShowIcon).show();
    }

    public static void toastSuccess(Context context, @NonNull String text) {
        Toasty.success(context, text, Toast.LENGTH_SHORT,false).show();
    }

    /**
     * 弹出错误消息
     *
     * @param text       需要显示的消息
     * @param isShowIcon 是否需要显示图标 默认显示
     */
    public static void toastError(Context context, @NonNull String text, Boolean isShowIcon) {
        Toasty.error(context, text, Toast.LENGTH_LONG, isShowIcon).show();
    }

    public static void toastError(Context context, @NonNull String text) {
        Toasty.error(context, text, Toast.LENGTH_LONG,false).show();
    }

    /**
     * 弹出一般消息
     *
     * @param text 需要显示的消息
     */
    public static void toastNormal(Context context, @NonNull String text) {
        Toasty.normal(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出警告消息
     *
     * @param text       需要显示的消息
     * @param isShowIcon 是否需要显示图标 默认显示
     */
    public static void toastWarn(Context context, @NonNull String text, Boolean isShowIcon) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT, isShowIcon).show();
    }

    public static void toastWarn(Context context, @NonNull String text) {
        Toasty.warning(context, text, Toast.LENGTH_SHORT,false).show();
    }
}
