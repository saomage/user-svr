package com.hfut.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.hfut.shopping.mapper")
@EnableDiscoveryClient
public class UserSvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSvrApplication.class, args);
	}

}

