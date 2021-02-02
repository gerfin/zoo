package com.zoo.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "用户名不可为空", groups = {RegisterGroup.class})
    private String username;
    @NotBlank(message = "密码不可为空", groups = {RegisterGroup.class, LoginGroup.class})
    @JsonIgnore
    private String password;
    @NotBlank(message = "手机号不可为空", groups = {RegisterGroup.class, LoginGroup.class})
    private String phone;
    private String avatar;
    private String accessToken;
    @JsonIgnore
    @CreatedDate
    private Date createdAt;
    @JsonIgnore
    @LastModifiedDate
    private Date updatedAt;

    public interface RegisterGroup{}
    public interface LoginGroup{}
}
