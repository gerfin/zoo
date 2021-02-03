package com.zoo.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "common")
@Configuration
public class GlobalWebConfigurer implements WebMvcConfigurer {
    private List<String> loginFilterPathList;
    private List<String> loginFilterExcludePathList;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration logRegistration = registry.addInterceptor(new WebLogInterceptor());
        InterceptorRegistration loginStatusInterceptor = registry.addInterceptor(new LoginStatusInterceptor());
        logRegistration.addPathPatterns("/**");
        if (loginFilterPathList.size() > 0) {
            loginStatusInterceptor.addPathPatterns(loginFilterPathList);
        }
        if (loginFilterExcludePathList.size() > 0) {
            loginStatusInterceptor.excludePathPatterns(loginFilterExcludePathList);
        }
    }
}
