package com.cloud.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Oauth2ResourceAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2ResourceAuthorizationApplication.class, args);
    }
}
