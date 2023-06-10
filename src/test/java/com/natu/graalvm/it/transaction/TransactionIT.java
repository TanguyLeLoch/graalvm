package com.natu.graalvm.it.transaction;

import com.natu.graalvm.domain.transaction.application.TransactionService;
import com.natu.graalvm.domain.transaction.core.model.AddTransactionCommand;
import com.natu.graalvm.it.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TransactionIT extends AbstractIT {

    private final TransactionService transactionService;

    @Autowired
    public TransactionIT(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Test
    public void createTransaction() {
        String hash = "0xe12a531d1ba228532b34920bfc2bc01b58f72c7268bf74f042457dcf840d9913";
        AddTransactionCommand command = AddTransactionCommand.builder()
                .hash(hash)
                .build();
        transactionService.addNewTransaction(command);

        TransactionResponseTest transactionResponseGet = getTransaction(hash);
        assertThat(transactionResponseGet.getHash()).isEqualTo(hash);
    }

    @Test
    public void getNotExistingTransactionShouldReturn404() {
        given()
                .contentType("application/json")
                .pathParam("hash", "invalid id")

                .when()
                .get("/transaction/{hash}")

                .then()
                .statusCode(404);
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
}
