package com.embarkx.jobms.job;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean   // this tells spring boot that this class/bean will be managed by springboot
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
