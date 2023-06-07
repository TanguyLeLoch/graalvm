package com.natu.graalvm.presentation;

import com.natu.graalvm.domain.User;

public class UserResponse {
    String id;

    UserResponse(User user) {
        this.id = user.getId();
    }

    UserResponse() {
        // for jackson
    }

    public String getId() {
        return id;
    }

}
