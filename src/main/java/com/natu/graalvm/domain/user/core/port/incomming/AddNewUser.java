package com.natu.graalvm.domain.user.core.port.incomming;

import com.natu.graalvm.domain.user.core.model.AddUserCommand;

public interface AddNewUser {
    void handle(AddUserCommand command);
}
