package com.gofirst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@MapperScan(basePackages = "com.gofirst.dao")
@SpringBootApplication
public class Start {

	public static void main(String[] args) {
		SpringApplication.run(Start.class, args);
	}
	
}
