package com.natu.graalvm.domain.user.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserInfraMongo {
    @Id
    String address;
    String status;

    public UserInfraMongo(User user) {
        this.address = user.getAddress();
        this.status = user.getStatus();
    }

    @PersistenceCreator
    public UserInfraMongo(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public User toDomain() {
        return new User(address, status);
    }
}
