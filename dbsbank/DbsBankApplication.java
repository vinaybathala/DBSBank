package com.dbsbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DbsBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbsBankApplication.class, args);
	}

}
