package com.jiuzhou.template.entity;

import lombok.Data;

@Data
public class UpFileEntity {
    private String size;

    private String msg;

    private String path;

    private String success;

    private int state;
}
