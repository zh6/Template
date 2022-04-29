package com.jiuzhou.template.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class BasePopEntity implements Serializable {
    private static final long serialVersionUID = 218519340632119771L;
    private Long id;
    private String code;
    private String name;
    private boolean check;
}
