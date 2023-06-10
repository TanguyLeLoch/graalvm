package com.natu.graalvm.presentation;

import com.natu.graalvm.MyService;
import com.natu.graalvm.application.UserResource;
import com.natu.graalvm.application.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    MyService myService;
    UserService userService;

    public UserController(MyService myService, UserService userService) {
        this.myService = myService;
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
