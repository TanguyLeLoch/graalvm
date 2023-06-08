package com.natu.graalvm.infrastructure;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.natu.graalvm.domain.User;
import com.natu.graalvm.presentation.NotFoundException;
import org.bson.Document;


public class UserRepositoryImpl implements UserRepository {

    private final MongoClient mongoClient;

    private final MongoDatabase userDatabase;
    private final MongoCollection<Document> collection;


    public UserRepositoryImpl(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        String databaseName = "graalvm";
        this.userDatabase = mongoClient.getDatabase(databaseName);
        this.collection = userDatabase.getCollection("user");
    }

    public User createUser(User user) {
        UserInfra userDocument = new UserInfra(user);
        collection.insertOne(userDocument);

        return userDocument.toDomain();
    }

    @Override
    public User getUser(Long id) {
        UserInfra document = (UserInfra) collection.find(new Document("id", id)).first();
        if (document == null) {
            throw new NotFoundException("User {0} not found", id);
        }
        return document.toDomain();
    }
}
