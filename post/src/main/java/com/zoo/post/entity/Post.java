package com.zoo.post.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long memberId;
    private String name;
    private String properties;
    private String description;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;
}
