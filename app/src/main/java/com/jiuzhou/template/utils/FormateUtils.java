package com.jiuzhou.template.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String bigDecimalToString(BigDecimal bd) {
        double value = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return doubleToString(value, "0.00");
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
    /**
     * 判断是不是大于0的数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^[0-9]+(.?[0-9]{1,3})?$");
        Matcher isNum = pattern.matcher(str);
        if(isNum.matches() ){
            return false;
        }
        return true;
    }
}