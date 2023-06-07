package com.natu.graalvm.presentation;

import com.natu.graalvm.MyService;
import com.natu.graalvm.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    MyService myService;

    public UserController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping()
    public UserResponse getUser() {
        String hello = myService.hello();
        System.out.println(hello);
        return new UserResponse(new User("1"));
    }
}
