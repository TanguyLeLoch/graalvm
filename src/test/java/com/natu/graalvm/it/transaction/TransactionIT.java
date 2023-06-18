package com.natu.graalvm.it.transaction;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.natu.graalvm.domain.transaction.application.TransactionService;
import com.natu.graalvm.domain.transaction.core.model.AddTransactionCommand;
import com.natu.graalvm.domain.transaction.infrastructure.EtherscanApi;
import com.natu.graalvm.it.AbstractIT;
import com.natu.graalvm.it.FileReader;
import com.natu.graalvm.it.WireMockConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToIgnoreCase;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
@PropertySource("classpath:secret.properties")
public class TransactionIT extends AbstractIT {

    private final TransactionService transactionService;
    private final WireMockServer mockEtherscanService;
    private final String apiKey;

    @Autowired
    public TransactionIT(TransactionService transactionService, WireMockServer mockEtherscanService, @Value("${etherscan.apikey}") String apiKey) {
        this.transactionService = transactionService;
        this.mockEtherscanService = mockEtherscanService;
        this.apiKey = apiKey;
    }

    @BeforeEach
    public void setUp() {
        super.setUp();
        mockEtherscanService.start();
        int port = mockEtherscanService.port();
        String host = "http://localhost";
        EtherscanApi.setBaseUrl(host + ":" + port);
    }

    @AfterEach
    public void tearDown() {
        mockEtherscanService.stop();
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

    @Test
    public void getNewTransactionFromBlockchain() throws IOException {
        ResponseDefinition response = getResponseFromFile("mock/tx-1.json");
        RequestPatternBuilder requestPatternBuilder = new RequestPatternBuilder();
        String address = "0xTestAddress";

        requestPatternBuilder
                .withHost(equalToIgnoreCase("localhost"))
                .withPort(mockEtherscanService.port())
                .withUrl("/api?module=account&action=txlist&address=" + address + "&startblock=0&endblock=99999999&sort=desc&apikey=" + apiKey);


        StubMapping stubMapping = new StubMapping(requestPatternBuilder.build(), response);
        mockEtherscanService.addStubMapping(stubMapping);
        transactionService.addNewTransactionFromBlockchain(address);


    }

    private static ResponseDefinition getResponseFromFile(String filePath) throws IOException {
        String body = FileReader.readFromResources(filePath);
        ResponseDefinitionBuilder response = aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(body);
        return response.build();
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

