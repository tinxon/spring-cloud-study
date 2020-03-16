package com.tinx.cloud.license;

import com.tinx.cloud.license.utils.UserContextInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient  //使用discovery client
@EnableCircuitBreaker
@EnableFeignClients     //使用feign client
@ServletComponentScan
public class LicenseServiceApplication {

    @Autowired
    private UserContextInterceptor interceptor;

    //使用带有Ribbon功能的Spring RestTemplate调用服务
    @LoadBalanced  //@LoadBalanced告诉Spring Cloud创建一个支持Ribbon的RestTemplate类
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            restTemplate.setInterceptors(Collections.singletonList(interceptor));
        } else {
            interceptors.add(interceptor);
        }
        return restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(LicenseServiceApplication.class, args);
    }

}
