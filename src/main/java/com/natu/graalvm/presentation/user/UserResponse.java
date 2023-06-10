package com.natu.graalvm.presentation.user;

import com.natu.graalvm.application.user.UserResource;

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
