package com.natu.graalvm.it.user;

import com.natu.graalvm.it.AbstractIT;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserIT extends AbstractIT {


    @Test
    public void createUser() {
        UserClient userClient = getUserClient();
        Map<String, String> parameters = new LinkedHashMap<>();
        String address = "0x1ED7F11679A47aC259FB7DD86A862A92bf0523e0";
        parameters.put("address", address);
        UserResponseTest userResponse = userClient.create(parameters);
        assertEquals(address, userResponse.getAddress());

        UserResponseTest userResponseGet = userClient.get(userResponse.getAddress());

        assertThat(userResponseGet).usingRecursiveComparison().isEqualTo(userResponse);
    }

    @Test
    public void getNotExistingUserShouldReturn404() {
        UserClient userClient = getUserClient();
        try {
            userClient.get("invalid id");
        } catch (FeignException e) {
            assertEquals(404, e.status());
        }
    }

    @Headers("Content-Type: application/json")
    public interface UserClient {
        @RequestLine("GET /{id}")
        UserResponseTest get(@Param("id") String id);

        @RequestLine("POST ?{parameters}")
        UserResponseTest create(@QueryMap Map<String, String> parameters);
    }
    // TODO replace with restassured ?

    private UserClient getUserClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(UserClient.class, "http://localhost:" + serverPort + "/user");
    }
}
