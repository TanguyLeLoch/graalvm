package com.natu.graalvm.it.transaction;

import com.natu.graalvm.it.AbstractIT;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionIT extends AbstractIT {

    @Test
    public void createTransaction() {
        String hash = "0xe12a531d1ba228532b34920bfc2bc01b58f72c7268bf74f042457dcf840d9913";
        TransactionResponseTest transactionResponse = given()
                .contentType("application/json")
                .queryParam("hash", hash)

                .when()
                .post("/transaction")

                .then()
                .statusCode(200)
                .body("hash", equalTo(hash))
                .extract().as(TransactionResponseTest.class);

        assertEquals(hash, transactionResponse.getHash());
        TransactionResponseTest transactionResponseGet = getTransaction(transactionResponse.getHash());
        assertThat(transactionResponseGet).usingRecursiveComparison().isEqualTo(transactionResponse);
    }

    private static TransactionResponseTest getTransaction(String hash) {
        return given()
                .pathParam("hash", hash)
                .when()
                .get("/transaction/{hash}")
                .then()
                .statusCode(200)
                .extract().as(TransactionResponseTest.class);
    }

    @Test
    public void getNotExistingTransactionShouldReturn404() {
        Response response = given()
                .contentType("application/json")
                .pathParam("hash", "invalid id")
                .when()
                .get("/transaction/{hash}");
        System.out.println(response.asString());
        response.then()
                .statusCode(404);

    }
}
