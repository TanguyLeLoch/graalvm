package com.natu.graalvm.it;

import com.natu.graalvm.GraalvmApplication;
import com.natu.graalvm.presentation.UserResponse;
import feign.Feign;
import feign.Headers;
import feign.RequestLine;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Profile("it")
public class UserIT {

    @BeforeAll
    public static void setUp() {
        GraalvmApplication.main(new String[]{});
    }

    @Test
    public void getUser() {
        UserClient userClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:9000/user");
        UserResponse userResponse = userClient.get();
        assertEquals("1", userResponse.getId());
    }

    @Headers("Content-Type: application/json")
    public interface UserClient {
        @RequestLine("GET")
        UserResponse get();
    }
}
