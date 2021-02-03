package com.zoo.post.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Category {
    private Long id;
    private String name;
    private Long parentId;
    private Date createdAt;
    private Date updatedAt;
}
