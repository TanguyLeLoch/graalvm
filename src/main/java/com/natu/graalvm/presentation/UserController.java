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

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        UserResource userResource = userService.getUser(id);
        return new UserResponse(userResource);
    }

    @PostMapping()
    public UserResponse createUser(@RequestParam String name) {
        return new UserResponse(userService.createUser(name));
    }
}
