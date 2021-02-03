package com.zoo.member.entity;

import lombok.*;
import java.util.Date;

@Data
public class Area {
    private Long id;
    private String name;
    private String parentId;
    private Integer level;
    private Date createdAt;
    private Date updatedAt;
}
