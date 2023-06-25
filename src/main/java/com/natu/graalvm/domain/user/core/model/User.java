package com.natu.graalvm.domain.user.core.model;

public class User {
    String address;
    String status;

    public User(String address) {
        this.address = address;
        this.status = "CREATED";
    }

    User(String address, String status) {
        this.address = address;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }
}
