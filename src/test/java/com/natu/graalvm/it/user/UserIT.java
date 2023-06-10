package com.natu.graalvm.it.user;

import com.natu.graalvm.domain.user.application.UserService;
import com.natu.graalvm.domain.user.core.model.AddUserCommand;
import com.natu.graalvm.it.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserIT extends AbstractIT {

    private final UserService userService;

    @Autowired
    public UserIT(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void createUser() {
        String address = "0xe12a531d1ba228532b34920bfc2bc01b58f72c7268bf74f042457dcf840d9913";
        AddUserCommand command = AddUserCommand.builder()
                .address(address)
                .build();
        userService.addNewUser(command);

        UserResponseTest userResponseGet = getUser(address);
        assertThat(userResponseGet.getAddress()).isEqualTo(address);
    }

    @Test
    public void getNotExistingUserShouldReturn404() {
        given()
                .contentType("application/json")
                .pathParam("address", "invalid id")

                .when()
                .get("/user/{address}")

                .then()
                .statusCode(404);
    }

    private static UserResponseTest getUser(String address) {
        return given()
                .pathParam("address", address)
                .when()
                .get("/user/{address}")
                .then()
                .statusCode(200)
                .extract().as(UserResponseTest.class);
    }
}
