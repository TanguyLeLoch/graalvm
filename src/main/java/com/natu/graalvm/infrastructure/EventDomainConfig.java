package com.natu.graalvm.infrastructure;


import com.natu.graalvm.domain.event.core.port.EventFacade;
import com.natu.graalvm.domain.event.infrastructure.EventDatabaseAdapter;
import com.natu.graalvm.domain.event.infrastructure.EventRepository;
import com.natu.graalvm.domain.transaction.core.port.outgoing.EventDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class EventDomainConfig {

    @Bean
    public EventDatabase eventDatabase(EventRepository eventRepository) {
        return new EventDatabaseAdapter(eventRepository);
    }

    @Bean
    public EventRepository eventRepository(MongoTemplate mongoTemplate) {
        return new EventRepository(mongoTemplate);
    }

    @Bean
    public EventFacade eventFacade(EventDatabase eventDatabase) {
        return new EventFacade(eventDatabase);
    }
}
