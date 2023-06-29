package com.natu.graalvm.domain.common;


import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.port.outgoing.Blockchain;
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
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Async;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class We3jApi implements Blockchain {

    private final Web3j web3j;
    private final String pairAbi = getPairAbi();

    We3jApi() {
        this.web3j = Web3j.build(new HttpService("https://ethereum.publicnode.com"), 1000, Async.defaultExecutorService());
    }

    @Override
    public Optional<Pair> getPair(String address) {
        Pair.Token token0 = getToken0(address);
        Pair.Token token1 = getToken1(address);
        return Optional.of(new Pair(address, token0, token1));
    }

    private Pair.Token getToken0(String address) {
        String token0Address = getStringInfo("token0", List.of(TypeReference.create(Address.class)), address);
        return getToken(token0Address);
    }

    private Pair.Token getToken1(String address) {
        String token0Address = getStringInfo("token1", List.of(TypeReference.create(Address.class)), address);
        return getToken(token0Address);
    }

    @NotNull
    private Pair.Token getToken(String tokenAddress) {
        int decimals = getDecimals(tokenAddress);
        String symbol = getStringInfo("symbol", List.of(TypeReference.create(Utf8String.class)), tokenAddress);
        String name = getStringInfo("name", List.of(TypeReference.create(Utf8String.class)), tokenAddress);
        return new Pair.Token(tokenAddress, symbol, name, decimals);
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
        EthCall response = null;
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

    private String getPairAbi() {
        return "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"tokenA\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"tokenB\",\"type\":\"address\"}],\"name\":\"getReserves\",\"outputs\":[{\"internalType\":\"uint112\",\"name\":\"reserve0\",\"type\":\"uint112\"},{\"internalType\":\"uint112\",\"name\":\"reserve1\",\"type\":\"uint112\"},{\"internalType\":\"uint32\",\"name\":\"blockTimestampLast\",\"type\":\"uint32\"}],\"stateMutability\":\"view\",\"type\":\"function\"}]";
    }
}
