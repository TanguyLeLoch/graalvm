package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserInfra {


    @Id
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
