package com.jiuzhou.template.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;

/**
 * 输入法相关工具类
 */
public class InputUtils {
    /**
     * 解决InputMethodManager内存泄露现象
     *
     * @param destContext
     */
    /**
     * 关闭输入法并解决InputMethodManager内存泄露现象
     *
     * @param activity 当前活动
     */
    public static void fixInputMethodManagerLeak(Activity activity) {
        if (activity == null) {
            return;
        }
        // 隐藏输入法
        hideSoftInput(activity);
        // 修复InputMethodManager内存泄露
        fixInputMethodManagerLeakInternal(activity);
    }

    /**
     * 隐藏输入法
     *
     * @param activity 当前活动
     */
    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * 修复InputMethodManager内存泄露
     *
     * @param context 目标上下文
     */
    private static void fixInputMethodManagerLeakInternal(Context context) {
        if (context == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f;
        Object obj_get;

        for (String param : arr) {
            try {
                f = imm.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                obj_get = f.get(imm);

                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == context) {
                        f.set(imm, null);
                    } else {
                        Logger.d("fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext() + " dest_context=" + context);
                        break;
                    }
                }
            } catch (Throwable t) {
                Logger.e(t, "fixInputMethodManagerLeak exception occurred for field: " + param);
            }
        }
    }
}
