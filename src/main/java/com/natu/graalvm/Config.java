package com.natu.graalvm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class Config {
    @Bean
    public MyService myService() {
        return new MyService();
    }
}

