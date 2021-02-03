package com.zoo.information.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Information {
    private Long id;
    private Integer type;
    private Long typeId;
    private String name;
    private String tel;
    private String address;
    private String latitude;
    private String longitude;
    private Date createdAt;
    private Date updatedAt;
}
