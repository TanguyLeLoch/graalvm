package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.user.core.port.UserFacade;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import com.natu.graalvm.domain.user.core.port.outgoing.UserDatabase;
import com.natu.graalvm.domain.user.infrastructure.UserDatabaseAdapter;
import com.natu.graalvm.domain.user.infrastructure.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class UserDomainConfig {

    @Bean
    public UserDatabase userDatabase(UserRepository userRepository) {
        return new UserDatabaseAdapter(userRepository);
    }

    @Bean
    public AddNewUser addNewUser(UserDatabase userDatabase) {
        return new UserFacade(userDatabase);
    }

    @Bean
    public RetrieveUser retrieveUser(UserDatabase userDatabase) {
        return new UserFacade(userDatabase);
    }
}
