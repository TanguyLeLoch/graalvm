package com.natu.graalvm.domain.transaction.application;

import org.springframework.scheduling.annotation.Scheduled;

public class Scheduler {
    @Scheduled(fixedDelay = 2000)
    public void scheduleFixed() {
        System.out.println("Hello World");
    }
}
