package com.natu.graalvm.infrastructure.user;

import com.natu.graalvm.domain.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserInfra {
    @Id
    String address;

    public UserInfra(User user) {
        this.address = user.getAddress();
    }

    @PersistenceCreator
    public UserInfra(String address) {
        this.address = address;
    }

    public User toDomain() {
        return new User(address);
    }
}
