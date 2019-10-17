package com.zh.template.network;

/**
 * <Pre>
 * 公共请求返回
 * </Pre>
 */
public class BaseResponse<T> {
    public int state;
    public String msg;
    public int dataCount;
    public T aaData;
}
