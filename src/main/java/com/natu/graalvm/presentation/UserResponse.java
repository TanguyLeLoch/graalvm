package com.natu.graalvm.presentation;

import com.natu.graalvm.domain.User;

public class UserResponse {
    String id;

    public UserResponse(User user) {
        this.id = user.getId();
    }

    public String getId() {
        return id;
    }
}
