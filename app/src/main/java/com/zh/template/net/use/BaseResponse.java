package com.zh.template.net.use;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private int state;
    private String msg;
    private int dataCount;
    private T aaData;
}
