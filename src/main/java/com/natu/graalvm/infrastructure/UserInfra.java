package com.natu.graalvm.infrastructure;

import com.natu.graalvm.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class UserInfra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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