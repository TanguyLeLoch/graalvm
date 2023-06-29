package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.user.core.port.UserFacade;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.AlterUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import com.natu.graalvm.domain.user.core.port.outgoing.RetrievePair;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;
import com.natu.graalvm.domain.user.infrastructure.UserDatabaseAdapter;
import com.natu.graalvm.domain.user.infrastructure.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class UserDomainConfig {

    @Bean
    public UserDatabase userDatabase(UserRepository userRepository, RetrievePair retrievePair) {
        return new UserDatabaseAdapter(userRepository, retrievePair);
    }

    @Bean
    public AddNewUser addNewUser(UserFacade userFacade) {
        return userFacade;
    }

    @Bean
    public RetrieveUser retrieveUser(UserFacade userFacade) {
        return userFacade;
    }

    @Bean
    public AlterUser alterUser(UserFacade userFacade) {
        return userFacade;
    }

    @Bean
    public UserFacade userFacade(UserDatabase userDatabase) {
        return new UserFacade(userDatabase);
    }

    @Bean
    public UserRepository userRepository(MongoTemplate mongoTemplate) {
        return new UserRepository(mongoTemplate);
    }
}
