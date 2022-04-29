package com.jiuzhou.template.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * 接口/按钮权限
 *
 * @author wangwei
 * @Date 2021/03/26
 */
@Data
public class SysButtonEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 中文名
     */
    private String zhName;

    /**
     * Url地址
     */
    private String api;

    /**
     * 模块Id
     */
    private Long moduleId;

    /**
     * 标识(1:可授权,0:默认授权)
     */
    private Long flag;

    /**
     * 创建时间
     */
    private String createdDate;

    /**
     * 创建人ID
     */
    private Long createdPersonId;

    /**
     * 更新时间
     */
    private String updatedDate;

    /**
     * 更新人ID
     */
    private Long updatedPersonId;

}