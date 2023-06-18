package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.transaction.core.port.TransactionFacade;
import com.natu.graalvm.domain.transaction.core.port.incomming.AddNewTransaction;
import com.natu.graalvm.domain.transaction.core.port.incomming.RetrieveTransaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.Blockchain;
import com.natu.graalvm.domain.transaction.core.port.outgoing.TransactionDatabase;
import com.natu.graalvm.domain.transaction.infrastructure.TransactionDatabaseAdapter;
import com.natu.graalvm.domain.transaction.infrastructure.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class TransactionDomainConfig {

    @Bean
    public TransactionDatabase transactionDatabase(TransactionRepository transactionRepository) {
        return new TransactionDatabaseAdapter(transactionRepository);
    }

    @Bean
    public AddNewTransaction addNewTransaction(TransactionDatabase transactionDatabase, Blockchain blockchain) {
        return new TransactionFacade(transactionDatabase, blockchain);
    }

    @Bean
    public RetrieveTransaction retrieveTransaction(TransactionDatabase transactionDatabase, Blockchain blockchain) {
        return new TransactionFacade(transactionDatabase, blockchain);
    }


//    @Bean
//    public Blockchain blockchain(EtherscanClient etherscanClient) {
//        return new EtherscanApi(etherscanClient);
//    }

    @Bean
    public TransactionRepository transactionRepository(MongoTemplate mongoTemplate) {
        return new TransactionRepository(mongoTemplate);
    }
}
