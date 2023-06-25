package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.event.application.EventService;
import com.natu.graalvm.domain.transaction.application.Scheduler;
import com.natu.graalvm.domain.transaction.application.TransactionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
@Configuration
@EnableScheduling
public class SchedulingConfig {

    @Bean
    public Scheduler scheduler(TransactionService transactionService, EventService eventService) {
        return new Scheduler(transactionService, eventService);
    }
}
