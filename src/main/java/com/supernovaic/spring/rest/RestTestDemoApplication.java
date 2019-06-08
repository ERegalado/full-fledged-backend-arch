package com.supernovaic.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RestTestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTestDemoApplication.class, args);
    }

}