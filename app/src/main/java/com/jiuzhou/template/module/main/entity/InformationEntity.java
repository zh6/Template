package com.jiuzhou.template.module.main.entity;

import java.io.Serializable;

/**
 * *收货信息实体类
 * *@author zhaohui
 * *@time 2019/8/13 11:25
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
public class InformationEntity implements Serializable {
    public Long id;

    /**
     * 用户Id.
     */
    public Long customerId;

    /**
     * 用户名称.
     */
    public String customerName;

    /**
     * 省Id.
     */
    public String provinceCode;

    /**
     * 省名称.
     */
    public String provinceName;

    /**
     * 市Id.
     */
    public String cityCode;

    /**
     * 市名称.
     */
    public String cityName;

    /**
     * 区Id.
     */
    public String districCode;

    /**
     * 区县名称.
     */
    public String districName;

    /**
     * 详细地址.
     */
    public String detailedAddress;

    /**
     * 完整地址信息.
     */
    public String completeAddress;

    /**
     * 联系人.
     */
    public String contacts;

    /**
     * 联系电话.
     */
    public String phoneNumber;

    /**
     * 备注.
     */
    public String comment;

    /**
     * 创建时间.
     */
    public String createdDate;

    /**
     * 是否默认地址.
     */
    public Integer isDefault;

    /**
     * 经度.
     */
    public String longitude;

    /**
     * 纬度.
     */
    public String latitude;
}
