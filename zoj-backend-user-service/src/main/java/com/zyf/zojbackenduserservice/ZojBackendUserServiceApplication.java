package com.zyf.zojbackenduserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zyf.zojbackenduserservice.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan("com.zyf")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.zyf.zojbackendserviceclient.service"})
public class ZojBackendUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZojBackendUserServiceApplication.class, args);
    }

}
