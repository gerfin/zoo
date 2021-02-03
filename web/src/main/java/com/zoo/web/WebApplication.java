package com.zoo.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@MapperScan(basePackages = "com.zoo.*.mapper")
@EntityScan(basePackages = "com.zoo.*.entity")
@EnableJpaRepositories(basePackages = "com.zoo.common.repository")
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"com.zoo.**"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
