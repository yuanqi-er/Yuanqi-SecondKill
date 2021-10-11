package com.yuanqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class YuanqiProductTypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanqiProductTypeApplication.class, args);
    }
}










