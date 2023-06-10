package com.natu.graalvm.domain.user.core.model;

public class UserResponse {

    User user;

    public UserResponse(User user) {
        this.user = user;
    }

    public String getAddress() {
        return user.getAddress();
    }


}
