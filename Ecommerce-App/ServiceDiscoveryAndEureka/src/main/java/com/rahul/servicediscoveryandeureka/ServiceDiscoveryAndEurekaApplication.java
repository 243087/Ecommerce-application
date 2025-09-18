package com.rahul.servicediscoveryandeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryAndEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryAndEurekaApplication.class, args);
		System.out.print("Hello WOrld ");
	}

}
