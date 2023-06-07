package com.natu.graalvm.it;

import com.natu.graalvm.presentation.UserResponse;
import feign.Feign;
import feign.RequestLine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserIT {

    @Test
    public void getUser() {
        UserClient userClient = Feign.builder()
                .target(UserClient.class, "http://localhost:8080/user");
        UserResponse userResponse = userClient.get();
        assertEquals("1", userResponse.getId());

    }

    public interface UserClient {

        @RequestLine("GET")
        UserResponse get();

    }

}
