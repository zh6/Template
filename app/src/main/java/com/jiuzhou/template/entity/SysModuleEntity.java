package com.jiuzhou.template.entity;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 模块权限
 *
 * @author wangwei
 * @Date 2021/03/26
 */
@Data
public class SysModuleEntity implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 路由
     */
    private String caption;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 扩展字段
     */
    private String extend;

    /**
     * 系统id
     */
    private Long sysId;

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


    private List<SysModuleEntity> chilModulList;

    private List<SysButtonEntity> modulButtonList;
}