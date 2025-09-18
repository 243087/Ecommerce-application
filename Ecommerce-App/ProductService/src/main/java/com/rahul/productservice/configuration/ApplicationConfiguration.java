package com.rahul.productservice.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ApplicationConfiguration {

    @Bean
    @LoadBalanced  // once we define this then RestTemplate will send the request randomly to service(authService) registered with eureka
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
