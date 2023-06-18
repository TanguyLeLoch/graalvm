package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.EtherscanResponse;
import com.natu.graalvm.domain.transaction.core.model.EtherscanTx;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.Blockchain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@PropertySource("classpath:secret.properties")
@Component
public class EtherscanApi implements Blockchain {

    @Value("${etherscan.apikey}")
    private String apiKey;

    private final EtherscanClient etherscanClient;
    private static URI baseUrl;

    public EtherscanApi(EtherscanClient etherscanClient, @Value("${etherscan.url}") String baseUrl) {
        this.etherscanClient = etherscanClient;
        EtherscanApi.baseUrl = URI.create(baseUrl);
    }

    public static void setBaseUrl(String baseUrl) {
        EtherscanApi.baseUrl = URI.create(baseUrl);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(EtherscanApi.class);

    public EtherscanResponse request(String module, String action, String address, String startBlock, String endBlock, String sort) {
        return etherscanClient.getRequest(baseUrl, module, action, address, startBlock, endBlock, sort, apiKey);
    }


    @Override
    public List<Transaction> getTransactions(String address, long startBlock, long endBlock) {
        EtherscanResponse response = request("account", "txlist", address, String.valueOf(startBlock), String.valueOf(endBlock), "desc");
        if (response.getStatus() == 1) {
            return response.getResult().stream().map(EtherscanTx::toDomain).toList();
        } else {
            LOGGER.error("Error from etherscan: {}", response.getMessage());
        }
        return List.of();
    }

}
