package com.natu.graalvm.presentation;

import com.natu.graalvm.application.UserResource;

public class UserResponse {

    UserResource userResource;

    UserResponse(UserResource userResource) {
        this.userResource = userResource;
    }

    UserResponse() {
        // for jackson
    }

    public String getAddress() {
        return userResource.getAddress();
    }


}
