package com.natu.graalvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.natu")
public class GraalvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraalvmApplication.class, args);
    }
}