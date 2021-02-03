package com.zoo.post.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long memberId;
    @NotNull(message = "分类id不可为空", groups = {Store.class})
    private Long categoryId;
    @NotNull(message = "名称不可为空", groups = {Store.class})
    private String name;
    private String properties;
    private String description;
    @JsonIgnore
    private Date createdAt;
    @JsonIgnore
    private Date updatedAt;

    public interface Store {}
}
