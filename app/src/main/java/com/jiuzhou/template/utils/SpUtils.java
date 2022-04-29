package com.jiuzhou.template.utils;

import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Set;

public enum SpUtils {
    DEFAULT("default"),
    SYSTEM("system"),
    USER("user");
    private MMKV mkv;

    SpUtils(String fileName) {
        mkv = MMKV.mmkvWithID(fileName);
    }

    public SpUtils putString(String key, String value) {
        mkv.encode(key, value);
        return this;
    }

    public String getString(String key) {
        return mkv.decodeString(key, "");
    }

    public String getStringDef(String key, String defValue) {
        return mkv.decodeString(key, defValue);
    }

    public SpUtils putBoolean(String key, boolean value) {
        mkv.encode(key, value);
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mkv.decodeBool(key, defValue);
    }


    public SpUtils putInt(String key, int value) {
        mkv.encode(key, value);
        return this;
    }

    public int getInt(String key, int defValue) {
        return mkv.decodeInt(key, defValue);
    }

    public SpUtils putFloat(String key, float value) {
        mkv.encode(key, value);
        return this;
    }

    public float getFloat(String key, float defValue) {
        return mkv.decodeFloat(key, defValue);
    }

    public SpUtils putLong(String key, long value) {
        mkv.putLong(key, value);
        return this;
    }

    public long getLong(String key) {
        return mkv.decodeLong(key, 0);
    }


    public SpUtils putSet(String key, Set<String> sets) {
        mkv.encode(key, sets);
        return this;
    }

    public SpUtils putParcelable(String key, Parcelable obj) {
        mkv.encode(key, obj);
        return this;
    }

    public Set<String> getSet(String key) {
        return mkv.decodeStringSet(key, Collections.emptySet());
    }

    public Parcelable getParcelable(String key, Class clz) {
        return mkv.decodeParcelable(key, clz);
    }

    /**
     * 移除某个key对
     *
     * @param key
     */
    public void removeKey(String key) {
        mkv.removeValueForKey(key);
    }

    /**
     * 移除多个key对
     *
     * @param keys
     */
    public void removeKeys(String[] keys) {
        mkv.removeValuesForKeys(keys);
    }

    /**
     * 获取全部key对
     */
    public String[] getAllKeys() {
        if (mkv.allKeys() != null) {
            return mkv.allKeys();
        }
        return new String[]{};
    }

    /**
     * 含有某个key
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return mkv.containsKey(key);
    }

    /**
     * 清除所有key
     */
    public void clear() {
        mkv.clearAll();
    }

    /**
     * 获取操作对象
     *
     * @return
     */
    public MMKV getMkv() {
        return mkv;
    }
}
