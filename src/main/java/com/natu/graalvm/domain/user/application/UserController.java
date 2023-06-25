package com.natu.graalvm.domain.user.application;

import com.natu.graalvm.domain.user.core.model.AddUserCommand;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.model.UserResponse;
import com.natu.graalvm.domain.user.core.port.incomming.AddNewUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RetrieveUser retrieveUser;
    private final AddNewUser addNewUser;


    public UserController(RetrieveUser retrieveUser, AddNewUser addNewUser) {
        this.retrieveUser = retrieveUser;
        this.addNewUser = addNewUser;
    }

    @PostMapping()
    public UserResponse getUser(@RequestBody AddUserCommand command) {
        User user = addNewUser.handle(command);
        return new UserResponse(user);
    }

    @GetMapping("/{address}")
    public UserResponse getUser(@PathVariable String address) {
        User user = retrieveUser.handle(address);
        return new UserResponse(user);
    }

}
