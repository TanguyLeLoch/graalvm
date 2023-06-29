package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.pair.core.port.PairFacade;
import com.natu.graalvm.domain.pair.core.port.incomming.AddNewPair;
import com.natu.graalvm.domain.pair.core.port.outgoing.Blockchain;
import com.natu.graalvm.domain.pair.core.port.outgoing.PairDatabase;
import com.natu.graalvm.domain.pair.infrastructure.PairDatabaseAdapter;
import com.natu.graalvm.domain.pair.infrastructure.PairRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class PairDomainConfig {

    @Bean
    public PairDatabase pairDatabase(PairRepository pairRepository) {
        return new PairDatabaseAdapter(pairRepository);
    }

    @Bean
    public AddNewPair addNewPair(PairDatabase pairDatabase, Blockchain blockchain) {
        return new PairFacade(pairDatabase, blockchain);
    }
}
