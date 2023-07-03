package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.AbstractEtherscanResult;
import com.natu.graalvm.domain.transaction.core.model.EtherscanTx;
import com.natu.graalvm.domain.transaction.core.model.EtherscanTxResponse;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@PropertySource("classpath:secret.properties")
@Component
@Slf4j
public class EtherscanApi {

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


    public EtherscanTxResponse request(String module, String action, String address, String startBlock, String endBlock, String sort) {
        long start = System.currentTimeMillis();
        EtherscanTxResponse etherscanResponse = etherscanClient.getTransactions(baseUrl, module, action, address, startBlock, endBlock, sort, apiKey);
        log.info("Etherscan tx request took {} ms for address {} and {} tx", System.currentTimeMillis() - start, address, etherscanResponse.getResult().size());
        return etherscanResponse;
    }


    public List<Transaction> getTransactions(String address) {
        Function<String, List<EtherscanTx>> requestData = (lastBlock) -> {
            EtherscanTxResponse response = request("account", "txlist", address, "0", lastBlock, "desc");
            return extractResult(response);
        };
        List<EtherscanTx> ethTx = getEtherscanData(requestData, 10000);
        return ethTx.stream().map(EtherscanTx::toDomain).toList();
    }


//    @Override
//    public List<Log> getTransactionLogs(String contractAddress, List<String> topics) {
//        if (topics.size() != 1) {
//            throw new RuntimeException("Only one topic supported now");
//        }
//        Function<String, List<EtherscanLog>> requestData = (lastBlock) -> {
//            EtherscanLogResponse response = requestLogs(contractAddress, topics, lastBlock);
//            return extractResult(response);
//        };
//        List<EtherscanLog> logs = getEtherscanData(requestData, 1000);
//        return logs.stream().map(EtherscanLog::toDomain).toList();
//    }

//    private EtherscanLogResponse requestLogs(String contractAddress, List<String> topics, String lastBlock) {
//        long start = System.currentTimeMillis();
//        EtherscanLogResponse etherscanResponse = etherscanClient.getLogs(baseUrl, "logs", "getLogs",
//                contractAddress, "0", lastBlock, topics.get(0), apiKey);
//        log.info("Etherscan log request took {} ms for contract {} and {} log", System.currentTimeMillis() - start, contractAddress, etherscanResponse.getResult().size());
//        return etherscanResponse;
//    }

//    private static List<EtherscanLog> extractResult(EtherscanLogResponse response) {
//        if (response.getStatus() == 1) {
//            return response.getResult();
//        } else {
//            log.error("Error from etherscan: {}", response.getMessage());
//            return List.of();
//        }
//    }

    private static List<EtherscanTx> extractResult(EtherscanTxResponse response) {
        if (response.getStatus() == 1) {
            return response.getResult();
        } else {
            log.error("Error from etherscan: {}", response.getMessage());
            return List.of();
        }
    }


    private <T extends AbstractEtherscanResult> List<T> getEtherscanData(Function<String, List<T>> requestData, int maxPerRequest) {
        List<T> result = new ArrayList<>();
        List<T> resultSalve;
        do {
            String lastBlock = result.isEmpty() ? "99999999" : result.get(result.size() - 1).getBlockNumber();
            resultSalve = requestData.apply(String.valueOf(lastBlock));
            result.addAll(resultSalve);
        } while (resultSalve.size() >= maxPerRequest);
        return result;
    }

}
