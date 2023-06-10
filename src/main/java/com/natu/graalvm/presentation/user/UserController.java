package com.natu.graalvm.presentation.user;

import com.natu.graalvm.application.user.UserResource;
import com.natu.graalvm.application.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{address}")
    public UserResponse getUser(@PathVariable String address) {
        UserResource userResource = userService.getUser(address);
        return new UserResponse(userResource);
    }

    @PostMapping()
    public UserResponse createUser(@RequestParam String address) {
        UserResource userResource = userService.createUser(address);
        return new UserResponse(userResource);
    }
}
