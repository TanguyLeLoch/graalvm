package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import org.bson.Document;
import org.springframework.data.annotation.PersistenceCreator;


public class UserInfra extends Document {

    String id;

    String name;


    public UserInfra(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    @PersistenceCreator
    public UserInfra(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User toDomain() {
        return new User(id, name);
    }
}
