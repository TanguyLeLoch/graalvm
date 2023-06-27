package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

@Getter
public class Log {
    private final String transactionHash;
    private final String transactionIndex;
    private final String address;
    private final List<String> topics;
    private final String data;
    private final Swap swap;

    public Log(String transactionHash, String transactionIndex, String address, List<String> topics, String data, Swap swap) {
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.address = address;
        this.topics = topics;
        this.data = data;
        this.swap = swap;
    }

    public Swap getSwap() {
        return swap;
    }

    public record Swap(BigInteger amount0In, BigInteger amount1In, BigInteger amount0Out, BigInteger amount1Out) {

    }

}
