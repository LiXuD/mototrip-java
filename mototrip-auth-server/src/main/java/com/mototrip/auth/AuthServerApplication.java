package com.mototrip.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
    "com.mototrip.auth",
    "com.mototrip.common.config",
    "com.mototrip.common.response",
    "com.mototrip.common.util",
    "com.mototrip.common.context",
    "com.mototrip.common.enums",
    "com.mototrip.common.constant"
})
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
