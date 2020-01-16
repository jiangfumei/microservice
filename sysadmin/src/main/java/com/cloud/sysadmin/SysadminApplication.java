package com.cloud.sysadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SysadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysadminApplication.class, args);
    }

}
