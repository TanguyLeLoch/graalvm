package com.natu.graalvm.presentation;

import com.natu.graalvm.MyService;
import com.natu.graalvm.application.UserResource;
import com.natu.graalvm.application.UserService;
import com.natu.graalvm.domain.User;
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

    @GetMapping()
    public UserResponse getUser() {
        String hello = myService.hello();
        System.out.println(hello);
        return new UserResponse(new UserResource(new User(34L, "test")));
    }

    @PostMapping()
    public UserResponse createUser(@RequestParam String name) {
        return new UserResponse(userService.createUser(name));
    }
}
