package com.cloudnative;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
    public class ProviderApplication {
    @Autowired
    private DiscoveryClient discoveryClient;

    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("PROVIDER");
        if (list != null && list.size() > 0 ) {
            return String.valueOf(list.get(0).getUri());
        }
        return null;
    }
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
