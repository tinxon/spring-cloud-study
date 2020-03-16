package com.tinx.cloud.specialroutes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpecialRouteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecialRouteServiceApplication.class, args);
    }

}
