package com.mototrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.mototrip.common.client")
@ComponentScan(basePackages = {
    "com.mototrip.route",
    "com.mototrip.common.config",
    "com.mototrip.common.response",
    "com.mototrip.common.util",
    "com.mototrip.common.context",
    "com.mototrip.common.enums",
    "com.mototrip.common.constant"
})
public class RouteServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RouteServiceApplication.class, args);
    }
}
