package com.jiuzhou.template.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * *输入控制
 * *@author zhaohui
 * *@time 2020/9/27 15:44
 */
public class InputValueFilter implements InputFilter {
    public InputValueFilter() {
    }
    public InputValueFilter(int pointerLength) {
        this.POINTER_LENGTH = pointerLength;
    }
    public InputValueFilter(double maxVALUE, int pointerLength) {
        this.MAX_VALUE = maxVALUE;
        this.POINTER_LENGTH = pointerLength;
    }

    Pattern mPattern = Pattern.compile("([0-9]|\\.)*");
    //输入的最大金额
    private double MAX_VALUE = 100000000;
    //小数点后的位数
    private int POINTER_LENGTH = 0;
    private final String POINTER = ".";
    private final String ZERO = "0";

    /**
     * CharSequence source ：本次输入内容
     * int start :本次输入被选择的起始位置
     * int end：本次输入被选择的结束位置(不包含)
     * Spanned dest ： 当前输入框中的内容
     * int dstart :被替换内容在输入框中的起始位置
     * int dend ：被替换内容在输入框中的结束位置(不包含)
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        //本次输入内容
        String sourceText = source.toString();
        //当前输入框中的内容
        String destText = dest.toString();
        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }
        //判断是否被全选
        if ((dend - dstart) == destText.length()) {
            destText = "";
        }
        //POINTER_LENGTH传0就不能输入小数
        if (POINTER_LENGTH == 0 && ".".equals(source)) {
            return "";
        }
        Matcher matcher = mPattern.matcher(source);
        if (!matcher.matches()) {
            return "";
        }
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (POINTER.equals(source)) {  //只能输入一个小数点
                return "";
            }
            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = destText.length() - index;
            if (length > POINTER_LENGTH && index < dstart) {
                //超出2位返回null
                return "";
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点
            if ((POINTER.equals(source)) && TextUtils.isEmpty(destText)) {
                return "";
            }
            if (destText.startsWith(ZERO) && !sourceText.equals(POINTER)) {
                return "";
            }
        }
        //验证输入金额的大小
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }
        if (destText.length() <= 0) {
            return sourceText;
        }
        return dest.subSequence(dstart, dend) + sourceText;
    }
}
