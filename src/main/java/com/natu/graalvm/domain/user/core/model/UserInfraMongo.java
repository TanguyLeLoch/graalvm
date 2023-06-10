package com.natu.graalvm.domain.user.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserInfraMongo {
    @Id
    String address;

    public UserInfraMongo(User user) {
        this.address = user.getAddress();
    }

    @PersistenceCreator
    public UserInfraMongo(String address) {
        this.address = address;
    }

    public User toDomain() {
        return new User(address);
    }
}
