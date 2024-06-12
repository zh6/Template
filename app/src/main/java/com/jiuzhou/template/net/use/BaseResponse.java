package com.jiuzhou.template.net.use;

public class BaseResponse<T> {

    private int state;
    private String msg;
    private int dataCount;
    private T aaData;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public T getAaData() {
        return aaData;
    }

    public void setAaData(T aaData) {
        this.aaData = aaData;
    }

}
