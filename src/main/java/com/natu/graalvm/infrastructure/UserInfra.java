package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import org.bson.Document;


public class UserInfra extends Document {

    Long id;

    String name;


    public UserInfra(User user) {
        this.id = user.getId();
        this.name = user.getName();
    }

    public UserInfra() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User toDomain() {
        return new User(id, name);
    }
}
