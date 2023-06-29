package com.natu.graalvm.domain.user.core.model;

import com.natu.graalvm.domain.pair.core.model.Pair;

import java.util.List;
import java.util.Map;

public class UserResponse {

    User user;

    public UserResponse(User user) {
        this.user = user;
    }

    public String getAddress() {
        return user.getAddress();
    }

    public String getStatus() {
        return user.getStatus();
    }

    public List<Pair> getPairs() {
        return user.getPairs();
    }

    public Map<String, Balance> getBalances() {
        return user.getBalances();
    }

}
