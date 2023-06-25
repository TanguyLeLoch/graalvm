package com.natu.graalvm.it.user;

import com.natu.graalvm.it.AbstractIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserIT extends AbstractIT {

    @Test
    public void createUser() {
        String address = "0xe12a531d1ba228532b34920bfc2bc01b58f72c7268bf74f042457dcf840d9913";

        given()
                .contentType("application/json")
                .body("{\"address\": \"" + address + "\"}")
                .when()
                .post("/users")

                .then()
                .statusCode(200);

        UserResponseTest userResponseGet = getUser(address);
        assertThat(userResponseGet.getAddress()).isEqualTo(address);
    }

    @Test
    public void getNotExistingUserShouldReturn404() {
        given()
                .contentType("application/json")
                .pathParam("address", "invalid id")

                .when()
                .get("/users/{address}")

                .then()
                .statusCode(404);
    }

    private static UserResponseTest getUser(String address) {
        return given()
                .pathParam("address", address)
                .when()
                .get("/users/{address}")
                .then()
                .statusCode(200)
                .extract().as(UserResponseTest.class);
    }
}
