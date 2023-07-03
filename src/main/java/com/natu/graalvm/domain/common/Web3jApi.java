package com.natu.graalvm.domain.common;


import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.model.Token;
import com.natu.graalvm.domain.transaction.core.model.Log;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Async;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class Web3jApi {

    private final Web3j web3j;
    private final static String SWAP_TOPIC = "0xd78ad95fa46c994b6551d0da85fc275fe613ce37657fb8d5e3d130840159d822";


    Web3jApi() {
        this.web3j = Web3j.build(new HttpService("https://ethereum.publicnode.com"), 1000, Async.defaultExecutorService());
    }

    public Optional<Pair> getPair(String address) {
        Token token0 = getToken0(address);
        Token token1 = getToken1(address);
        return Optional.of(new Pair(address, token0, token1));
    }

    public List<Log> getTransactionLogs(String txHash) {
        try {
            return getTxLogThrowingException(txHash);
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Log> getTxLogThrowingException(String txHash) throws ExecutionException, InterruptedException, IOException {
        EthGetTransactionReceipt transactionReceipt = this.web3j.ethGetTransactionReceipt(txHash).send();
        if (transactionReceipt.getTransactionReceipt().isEmpty()) {
            log.error("Transaction receipt is empty for txHash: {}", txHash);
            return List.of();
        }

        TransactionReceipt result = transactionReceipt.getTransactionReceipt().get();
        List<Log> logs = new ArrayList<>();
        for (org.web3j.protocol.core.methods.response.Log log : result.getLogs()) {
            String txIndex = result.getTransactionIndex().toString();
            String address = log.getAddress();
            List<String> topics = log.getTopics();
            String data = log.getData();
            Log.Swap swap = null;
            if (topics.contains(SWAP_TOPIC)) {
                String dataHex = data.split("0x")[1];
                String amount0In = dataHex.substring(0, 64);
                String amount1In = dataHex.substring(64, 128);
                String amount0Out = dataHex.substring(128, 192);
                String amount1Out = dataHex.substring(192, 256);
                swap = new Log.Swap(hexToBigInteger(amount0In), hexToBigInteger(amount1In),
                        hexToBigInteger(amount0Out), hexToBigInteger(amount1Out));
                logs.add(new Log(txHash, txIndex, address, topics, data, swap));
            }
        }
        return logs;
    }

    private BigInteger hexToBigInteger(String hex) {
        return new BigInteger(hex, 16);
    }

    private Token getToken0(String address) {
        String token0Address = getStringInfo("token0", List.of(TypeReference.create(Address.class)), address);
        return getToken(token0Address);
    }

    private Token getToken1(String address) {
        String token0Address = getStringInfo("token1", List.of(TypeReference.create(Address.class)), address);
        return getToken(token0Address);
    }

    @NotNull
    private Token getToken(String tokenAddress) {
        int decimals = getDecimals(tokenAddress);
        String symbol = getStringInfo("symbol", List.of(TypeReference.create(Utf8String.class)), tokenAddress);
        String name = getStringInfo("name", List.of(TypeReference.create(Utf8String.class)), tokenAddress);
        return new Token(tokenAddress, symbol, name, decimals);
    }

    private int getDecimals(String token0Address) {
        Function getDecimals = new Function("decimals", List.of(), List.of(TypeReference.create(Uint.class)));
        BigInteger decimals = ((Uint) callFunction(token0Address, getDecimals)).getValue();
        return decimals.intValue();
    }

    private String getStringInfo(String token0, List<TypeReference<?>> outputParameters, String address) {
        Function function = new Function(token0, List.of(), outputParameters);
        return callFunction(address, function).toString();
    }

    private Type callFunction(String address, Function function) {
        String encodedFunction = FunctionEncoder.encode(function);
        EthCall response;
        try {
            response = web3j.ethCall(
                            Transaction.createEthCallTransaction(address, address, encodedFunction),
                            DefaultBlockParameterName.LATEST)
                    .sendAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        //convert to address
        List<Type> decoded = FunctionReturnDecoder.decode(response.getValue(), function.getOutputParameters());
        return decoded.get(0);
    }
}
