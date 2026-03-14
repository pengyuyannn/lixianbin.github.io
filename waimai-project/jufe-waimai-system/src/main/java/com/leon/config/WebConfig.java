package com.leon.config;

import com.leon.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类 - 注册拦截器
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {
    
//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")  // 拦截所有请求
//                .excludePathPatterns(
//                    "/login",      // 排除登录接口
//                    "/error",           // 排除错误页面
//                    "/upload-test.html" // 排除测试页面
//                );
//    }
}
