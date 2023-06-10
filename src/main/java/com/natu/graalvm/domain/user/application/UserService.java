package com.natu.graalvm.domain.user.application;

import com.natu.graalvm.domain.user.core.model.AddUserCommand;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final AddNewUser addNewUser;

    public UserService(AddNewUser addNewUser) {
        this.addNewUser = addNewUser;
    }

    public void addNewUser(AddUserCommand command) {
        addNewUser.handle(command);
    }

}
