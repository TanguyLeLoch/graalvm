package com.natu.graalvm.domain.user.core.port.incomming;

import com.natu.graalvm.domain.user.core.model.AddUserCommand;
import com.natu.graalvm.domain.user.core.model.User;

public interface AddNewUser {
    User handle(AddUserCommand command);
}
