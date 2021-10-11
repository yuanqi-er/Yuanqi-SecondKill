package com.yuanqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@EnableFeignClients(basePackages = {"com.yuanqi"})
@SpringBootApplication
@EnableEurekaClient
public class YuanqiSecKillProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(YuanqiSecKillProductApplication.class, args);
    }
}










