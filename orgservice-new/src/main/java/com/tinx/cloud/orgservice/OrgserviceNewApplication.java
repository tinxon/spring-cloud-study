package com.tinx.cloud.orgservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrgserviceNewApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrgserviceNewApplication.class, args);
    }

}
