package com.cloud.sysadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@EntityScan("com.cloud.common.base.entity")
@EnableJpaRepositories(basePackages ={"com.cloud.sysadmin.repository"})
@ComponentScan(value = {"com.cloud.sysadmin.controller","com.cloud.sysadmin.service"})
public class SysadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysadminApplication.class, args);
    }

}
