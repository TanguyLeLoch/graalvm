package com.natu.graalvm.domain.user.application;

import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.model.UserResponse;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final RetrieveUser retrieveUser;

    public UserController(RetrieveUser retrieveUser) {
        this.retrieveUser = retrieveUser;
    }

    @GetMapping("/{address}")
    public UserResponse getUser(@PathVariable String address) {
        User user = retrieveUser.handle(address);

        return new UserResponse(user);
    }
}
