package com.jiuzhou.template.entity;

import lombok.Data;

/**
 * 角色
 *
 * @author wangwei
 * @Date 2021/03/26
 */
@Data
public class SysRoleEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 系统ID
     */
    private Long sysId;

    /**
     * 用户类型id
     */
    private String typeId;

    /**
     * 用户类型编码
     */
    private String typeCode;

}