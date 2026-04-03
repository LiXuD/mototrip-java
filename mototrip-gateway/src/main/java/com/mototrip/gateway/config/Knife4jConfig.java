package com.mototrip.gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SpringDocConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Knife4jConfig {

    @Bean
    public SpringDocConfiguration springDocConfiguration() {
        return new SpringDocConfiguration();
    }

    @Bean
    public SpringDocConfigProperties springDocConfigProperties() {
        return new SpringDocConfigProperties();
    }

    @Bean
    public List<GroupedOpenApi> groupedOpenApis() {
        List<GroupedOpenApi> list = new ArrayList<>();

        // Auth Service
        list.add(GroupedOpenApi.builder()
                .group("1-认证服务")
                .pathsToMatch("/api/auth/**")
                .build());

        // User Center
        list.add(GroupedOpenApi.builder()
                .group("2-用户中心")
                .pathsToMatch("/api/users/**", "/api/user-mode/**")
                .build());

        // Route Service
        list.add(GroupedOpenApi.builder()
                .group("3-路线服务")
                .pathsToMatch("/api/routes/**", "/api/waypoints/**", "/api/reviews/**")
                .build());

        // Trip Service
        list.add(GroupedOpenApi.builder()
                .group("4-行程服务")
                .pathsToMatch("/api/trips/**", "/api/diaries/**", "/api/preparations/**", "/api/shares/**")
                .build());

        // Social Service
        list.add(GroupedOpenApi.builder()
                .group("5-社交服务")
                .pathsToMatch("/api/posts/**", "/api/comments/**", "/api/footprints/**")
                .build());

        // Map & Safety Service
        list.add(GroupedOpenApi.builder()
                .group("6-地图安全服务")
                .pathsToMatch("/api/maps/**", "/api/danger-zones/**", "/api/no-parking-zones/**", "/api/warnings/**", "/api/locations/**")
                .build());

        // Team Service
        list.add(GroupedOpenApi.builder()
                .group("7-车队服务")
                .pathsToMatch("/api/teams/**")
                .build());

        // Weather Service
        list.add(GroupedOpenApi.builder()
                .group("8-天气服务")
                .pathsToMatch("/api/weather/**")
                .build());

        return list;
    }
}
