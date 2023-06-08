package com.natu.graalvm;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.natu.graalvm.infrastructure.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.natu")
public class Config {

    @Value("${mongo.uri}")
    private String mongoUri;
    @Value("${mongo.database}")
    private String mongoDatabase;


    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public UserRepositoryImpl userRepository(MongoClient mongoClient) {
        return new UserRepositoryImpl(mongoClient);
    }

}

