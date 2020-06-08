package com.gofirst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@MapperScan(basePackages = "com.gofirst.dao")
@EnableScheduling
@SpringBootApplication
public class Start {

	public static void main(String[] args) {
		SpringApplication.run(Start.class, args);
	}
	
}
