package com.natu.graalvm.it;

import com.natu.graalvm.GraalvmApplication;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class UserIT {

    @BeforeAll
    public static void setUp() {
        GraalvmApplication.main(new String[]{});
    }

    @Test
    public void createUser() {
        UserClient userClient = getUserClient();
        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("name", "new user");
        UserResponseTest userResponse = userClient.create(parameters);
        assertEquals("new user", userResponse.getName());

        UserResponseTest userResponseGet = userClient.get(userResponse.getId());

        assertThat(userResponseGet).usingRecursiveComparison().isEqualTo(userResponse);
    }

    @Test
    public void getNotExistingUserShouldReturn404() {
        UserClient userClient = getUserClient();
        try {
            userClient.get(-999L);
        } catch (FeignException e) {
            assertEquals(404, e.status());
        }
    }

    @Headers("Content-Type: application/json")
    public interface UserClient {
        @RequestLine("GET /{id}")
        UserResponseTest get(@Param("id") Long id);

        @RequestLine("POST ?{parameters}")
        UserResponseTest create(@QueryMap Map<String, String> parameters);
    }
    // TODO replace with restassured ?

    private UserClient getUserClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:9000/user");
    }
}
