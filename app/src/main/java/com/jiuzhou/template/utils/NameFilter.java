package com.jiuzhou.template.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/7/18.
 */

public class NameFilter implements InputFilter {
    private int mMaxLen = 10;//最大长度
    private double Maximum=0;//最大值
    private int MaxDigits=0;//最大小数位数
    public NameFilter() {
    }

    public NameFilter(int mMaxLen, double Maximum, int MaxDigits) {
        this.mMaxLen=mMaxLen;
        this.Maximum = Maximum;
        this.MaxDigits = MaxDigits;
    }
        @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String sourceText = source.toString();
            String destText = dest.toString();
            //验证删除等按键
            if (TextUtils.isEmpty(sourceText)) {
                return "";
            }
            //判断是否被全选
            if((dend-dstart)==destText.length()){
                destText="";
            }
            Pattern mPattern = Pattern.compile("([0-9]|\\.)*");
            Matcher matcher = mPattern.matcher(source);
            if (!matcher.matches()) {
                return "";
            }
            // 已经输入小数点的情况下，只能输入数字
            if (destText.contains(".")) {
                if (".".equals(source)) { //只能输入一个小数点
                    return "";
                }
                double sumText = Double.parseDouble(destText + sourceText);
                if (sumText > Maximum) {
                    return dest.subSequence(dstart, dend);
                }
                //验证小数点精度，保证小数点后只能输入2位
                int index = destText.indexOf(".");
                int length = dend - index;
                if (destText != null && destText.length() > 0 && destText.contains(".")) {
                    String[] amoArr = destText.split("\\.");
                    int indexPoint = destText.indexOf(".");
                    if (dstart <= indexPoint) {
                        if (dend > 0) {
                            String temp = amoArr[0].substring(0, dend - 1) + sourceText + amoArr[0].substring(dend - 1, amoArr[0].length());
                            if (Double.parseDouble(temp) > Maximum) {
                                return "";
                            }
                        }
                    } else if (amoArr.length > 1 && amoArr[amoArr.length - 1].length() >= MaxDigits) {
                        return "";
                    }
                }
            } else { // 还没有输入小数点.的情况
                if (source.equals(".") && destText.length() == 0) {
                    return "0.";
                } else {
                    if (destText != null && destText.length() > 0) {
                        double sumDou = Double.parseDouble(destText);
                        if (sumDou > Maximum) {
                            return "";
                        }
                    }
                    String[] amoArr = (destText).split("\\.");
                    int indexPoint = destText.indexOf(".");
                    //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                    if (start < end && indexPoint < 0 && destText.equals("0") && !source.equals(".")) {
                        return "";
                    }
                    if (dstart <= indexPoint) {
                        if (destText != null && destText.length() > 0) {
                            double sumDou = Double.parseDouble(destText);
                            if (sumDou > Maximum) {
                                return destText.subSequence(dstart, dend);
                            }
                        }
                    } else if (amoArr.length > 1 && amoArr[amoArr.length - 1].length() >= 2) {
                        return "";
                    } else if (source.equals(".") && destText.length() - dstart > 2) {
                        return "";
                    }
                }
            }
            //验证可以输入长度
            if ((destText + sourceText).length() > mMaxLen) {
                return destText.subSequence(dstart, dend);
            }
            //验证输入金额的大小
            double sumText = Double.parseDouble(destText + sourceText);
            if (sumText > Maximum) {
                return destText.subSequence(dstart, dend);
            }
            if(destText.length()<=0) {
                return sourceText;
            }
            return destText.subSequence(dstart, dend) + sourceText;

    }
}
