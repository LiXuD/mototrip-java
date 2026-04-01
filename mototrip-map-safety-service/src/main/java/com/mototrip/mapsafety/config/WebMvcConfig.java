package com.mototrip.mapsafety.config;

import com.mototrip.common.interceptor.SentinelResourceInterceptor;
import com.mototrip.common.interceptor.UserContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Sentinel rate limiting (highest priority)
        registry.addInterceptor(new SentinelResourceInterceptor())
                .addPathPatterns("/api/**")
                .order(0);
        // User context extraction
        registry.addInterceptor(new UserContextInterceptor())
                .addPathPatterns("/api/**")
                .order(1);
    }
}
