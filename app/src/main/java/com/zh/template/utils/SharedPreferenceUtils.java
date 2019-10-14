package com.zh.template.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象存储类
 */
public class SharedPreferenceUtils {
    public static void saveIp(Context context, String ip) {
        SharedPreferences spf = context.getSharedPreferences("ip", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("IP", ip);
        editor.commit();
    }

    /**
     * @param context
     * @return
     */
    public static String getIp(Context context) {
        SharedPreferences spf = context.getSharedPreferences("ip", Context.MODE_PRIVATE);
        String ip = spf.getString("IP", "http://xysc.yshfresh.com/");
//        return ip;
        return "http://114.67.99.110:9200/";
    }

    /**
     * 取token
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        SharedPreferences spf = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return spf.getString("token", "");
    }

    /**
     * 存token
     *
     * @param context
     * @param token
     */
    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor et = sp.edit();
        et.putString("token", token);
        et.commit();
    }

    /**
     * 存用户信息
     */
    public static void saveUserInfo(Context context, String name, String psd) {
        SharedPreferences spf = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("name", name);
        editor.putString("psd", psd);
        editor.commit();
    }

    /**
     * 取用户信息
     */
    public static List<String> getUserInfo(Context context) {
        List<String> userInfo = new ArrayList<>();
        SharedPreferences spf = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userInfo.add(spf.getString("name", ""));
        userInfo.add(spf.getString("psd", ""));
        return userInfo;
    }
}
