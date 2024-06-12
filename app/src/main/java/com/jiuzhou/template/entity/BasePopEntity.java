package com.jiuzhou.template.entity;

import java.io.Serializable;


public class BasePopEntity implements Serializable {
    private static final long serialVersionUID = 218519340632119771L;
    private Long id;
    private String code;
    private String name;
    private boolean check;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
