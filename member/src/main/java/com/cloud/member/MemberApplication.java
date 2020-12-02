package com.cloud.member;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
public class MemberApplication {

	public static void main(String[] args) {
		run(MemberApplication.class, args);
	}

}
