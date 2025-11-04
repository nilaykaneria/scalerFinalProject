package com.example.productcatalogservice_nov2024.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @LoadBalanced //TO MAKE THIS MICRO SERVICE CLIENT LB
    //THAT'S THE ONLY REASON WE CREATED THIS REST TEMPLATE CLASS
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
