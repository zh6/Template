package com.zh.template.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zh.template.base.MyApplication;

/**
 * （SharedPreferences）工具类----枚举类
 * 每个实例对应一个sp文件，如，SYSTEM对应system文件，USER对应user文件…
 * <p>
 * Update by wangzhengyang on 2017/8/21.
 * <p>
 * 使用：
 * <p>
 * 切换文件写入读取只需要一个“.”
 * SPUtil.SYSTEM.……  //切换到system文件读写
 * SPUtil.USER.……   //切换到user文件读写
 * <p>
 * //单个写入提交
 * SPUtil.USER.putInt("age", 32).commit();
 * <p>
 * //链式调度写入提交（Consts：一个保存常量的类）
 * SPUtil.USER
 * .putString(Consts.KEY_NAME, "张三")
 * .putString(Consts.KEY_SEX, "男")
 * .putInt(Consts.KEY_AGE, 23)
 * .commit();
 * <p>
 * //读取
 * String name = SPUtil.USER.getString(Consts.KEY_NAME, "");
 * String sex = SPUtil.USER.getString(Consts.KEY_SEX, "");
 * int age = SPUtil.USER.getInt(Consts.KEY_AGE, 0);
 * Log.i(TAG, "name: " + name + "\tsex: "+ sex + "\tage: " + age);
 * <p>
 * //根据key移除
 * boolean isRemoved = SPUtil.USER.remove(Consts.USER_NAME);
 * <p>
 * //清除所有
 * boolean isClear = SPUtil.USER.clear();
 */
public enum SPUtil {
    DEFAULT("default"),
    SYSTEM("system"),
    DEVICE("device"),
    USER("user");

    private final SharedPreferences SHARED_PREFS;
    private final String FILE_NAME;
    private final SharedPreferences.Editor EDITOR;

    SPUtil(String fileName) {
        this.FILE_NAME = fileName;
        SHARED_PREFS = MyApplication.getAppContext().getSharedPreferences(this.FILE_NAME, Context.MODE_PRIVATE);
        EDITOR = SHARED_PREFS.edit();
    }

    /**
     * 获得文件名
     *
     * @return 文件名
     */
    public String getFileName() {
        return FILE_NAME;
    }

    public SPUtil putString(String key, String value) {
        //if (TextUtils.isEmpty(key)) return false;
        EDITOR.putString(key, value);
        return this;
    }

    public String getString(String key, String defValue) {
        //if (TextUtils.isEmpty(key)) return null;
        return SHARED_PREFS.getString(key, defValue);
    }

    public SPUtil putBoolean(String key, boolean value) {
        //if (TextUtils.isEmpty(key)) return false;
        EDITOR.putBoolean(key, value);
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        //if (TextUtils.isEmpty(key)) return false;
        return SHARED_PREFS.getBoolean(key, defValue);
    }


    public SPUtil putInt(String key, int value) {
        EDITOR.putInt(key, value);
        return this;
    }

    public int getInt(String key, int defValue) {
        return SHARED_PREFS.getInt(key, defValue);
    }

    public SPUtil putFloat(String key, float value) {
        EDITOR.putFloat(key, value);
        return this;
    }

    public float getFloat(String key, float defValue) {
        return SHARED_PREFS.getFloat(key, defValue);
    }

    public SPUtil putLong(String key, long value) {
        EDITOR.putLong(key, value);
        return this;
    }

    public long getLong(String key, long defValue) {
        return SHARED_PREFS.getLong(key, defValue);
    }

    /**
     * 根据key移除
     *
     * @param key
     * @return
     */
    public boolean remove(String key) {
        return EDITOR.remove(key).commit();
    }

    /**
     * 清除SP文件中的所有数据
     *
     * @return
     */
    public boolean clear() {
        return EDITOR.clear().commit();
    }

    /**
     * 提交
     *
     * @return
     */
    public boolean commit() {
        return EDITOR.commit();
    }
}
