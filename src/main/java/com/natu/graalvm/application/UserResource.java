package com.natu.graalvm.application;

import com.natu.graalvm.domain.User;


public class UserResource {
    User user;

    public UserResource(User user) {
        this.user = user;
    }

    public String getAddress() {
        return user.getAddress();
    }


}