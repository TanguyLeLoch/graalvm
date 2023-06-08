package com.natu.graalvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.natu")
@EnableMongoRepositories(basePackages = "com.natu")
public class GraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraalvmApplication.class, args);
    }
}