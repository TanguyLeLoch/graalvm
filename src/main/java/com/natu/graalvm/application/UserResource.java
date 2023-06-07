package com.natu.graalvm.application;

import com.natu.graalvm.domain.User;


public class UserResource {
    User user;

    public UserResource(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

}