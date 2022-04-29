package com.jiuzhou.template.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 用户登录返回对象
 *
 * @author lansongtao
 * @Date 2017年4月12日
 * @since 1.0
 */
@Data
public class LoginRespEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户可见的菜单
     */
    private List<ApiSysModuleEntity> sysModuleVOList;

    /**
     * 用户所属的角色
     */
    private List<SysRoleEntity> sysRoleVOS;

    /**
     * 登录用户信息(返回给前端的)
     */
    private UserEntity userVO;
    private int state;
    private String msg;
    /**
     * 登录TOKEN
     */
    private String token;

    /**
     * 是否提醒用户修改密码
     */
    private boolean isUpdatePwd;
}
