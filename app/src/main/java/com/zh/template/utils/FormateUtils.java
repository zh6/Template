package com.zh.template.utils;

import java.text.DecimalFormat;

/**
 * *格式化数字
 * *@author zhaohui
 * *@time 2019/10/8 10:56
 * * ----------Dragon be here!----------/
 * * 　　　┏┓　　 ┏┓
 * * 　　┏┛┻━━━┛┻┓━━━
 * * 　　┃　　　　　 ┃
 * * 　　┃　　　━　  ┃
 * * 　　┃　┳┛　┗┳
 * * 　　┃　　　　　 ┃
 * * 　　┃　　　┻　  ┃
 * * 　　┃　　　　   ┃
 * * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * * 　　　　┃　　　┃    神兽保佑,代码无bug
 * * 　　　　┃　　　┃
 * * 　　　　┃　　　┗━━━┓
 * * 　　　　┃　　　　　　┣┓
 * * 　　　　┃　　　　　　　┏┛
 * * 　　　　┗┓┓┏━┳┓┏┛━━━━━
 * * 　　　　　┃┫┫　┃┫┫
 * * 　　　　　┗┻┛　┗┻┛
 * * ━━━━━━━━━━━神兽出没━━━━━━━━━━━━━━
 */
public class FormateUtils {
    /**
     * double转string，保留两位小数
     *
     * @param d
     * @param formateStr 0.00保留两位；0.000保留三位
     * @return
     */
    public static String doubleToString(double d, String formateStr) {
        DecimalFormat decimalFormat = new DecimalFormat(formateStr);//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(d);//format 返回的是字符串
        return p;
    }

    /**
     * string 转double
     *
     * @param s
     * @return
     */
    public static double stringToDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;
    }
}