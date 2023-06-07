package com.natu.graalvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.natu")
@EnableJpaRepositories(basePackages = "com.natu.graalvm.infrastructure")
public class GraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraalvmApplication.class, args);
    }
}