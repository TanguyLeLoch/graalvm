package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.transaction.core.port.TransactionFacade;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;
import com.natu.graalvm.domain.transaction.infrastructure.TransactionDatabaseAdapter;
import com.natu.graalvm.domain.transaction.infrastructure.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class TransactionDomainConfig {

    @Bean
    public TransactionDatabase transactionDatabase(TransactionRepository transactionRepository) {
        return new TransactionDatabaseAdapter(transactionRepository);
    }

    @Bean
    public AddNewTransaction addNewTransaction(TransactionDatabase transactionDatabase) {
        return new TransactionFacade(transactionDatabase);
    }

    @Bean
    public RetrieveTransaction retrieveTransaction(TransactionDatabase transactionDatabase) {
        return new TransactionFacade(transactionDatabase);
    }
}
