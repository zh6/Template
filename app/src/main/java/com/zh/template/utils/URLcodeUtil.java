package com.zh.template.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLcodeUtil {
    /**
     * 字符串编码成URLcode编码
     */
    public static String encode(String src) {
        String result = "";
        try {
            result = URLEncoder.encode(src, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URLcode解码成字符串
     *
     * @param src
     * @return
     */
    public static String decode(String src) {
        String result = "";
        try {
            result = URLDecoder.decode(src, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
