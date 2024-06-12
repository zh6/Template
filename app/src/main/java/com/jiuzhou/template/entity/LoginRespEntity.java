package com.jiuzhou.template.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录返回对象
 *
 * @author lansongtao
 * @Date 2017年4月12日
 * @since 1.0
 */
public class LoginRespEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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


    public UserEntity getUserVO() {
        return userVO;
    }

    public void setUserVO(UserEntity userVO) {
        this.userVO = userVO;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUpdatePwd() {
        return isUpdatePwd;
    }

    public void setUpdatePwd(boolean updatePwd) {
        isUpdatePwd = updatePwd;
    }
}
