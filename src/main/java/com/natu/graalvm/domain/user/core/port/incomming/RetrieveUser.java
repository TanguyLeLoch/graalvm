package com.natu.graalvm.domain.user.core.port.incomming;

import com.natu.graalvm.domain.user.core.model.User;

public interface RetrieveUser {
    User handle(String address);
}
