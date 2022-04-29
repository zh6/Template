package com.jiuzhou.template.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class AddressEntity  implements Serializable {
    private String areaCode;
    private Long id;
    private String areaName;
    private int level;
    private String parentAreaCode;
    private Boolean Status;

}
