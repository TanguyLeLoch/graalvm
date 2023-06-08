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

    public String getId() {
        return userResource.getId();
    }

    public String getName() {
        return userResource.getName();
    }


}
