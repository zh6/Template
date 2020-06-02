package com.zh.template.module.main.entity;

import com.zh.template.net.use.BaseResponse;

import java.io.Serializable;

public class AddressEntity extends BaseResponse implements Serializable {
    public String areaCode;
    public Long id;
    public String areaName;
    public int level;
    public String parentAreaCode;
    public Boolean Status;
}
