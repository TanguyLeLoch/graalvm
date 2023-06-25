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

    static class Swap {

        private final BigInteger amount0In;
        private final BigInteger amount1In;
        private final BigInteger amount0Out;
        private final BigInteger amount1Out;

        public Swap(BigInteger amount0In, BigInteger amount1In, BigInteger amount0Out, BigInteger amount1Out) {
            this.amount0In = amount0In;
            this.amount1In = amount1In;
            this.amount0Out = amount0Out;
            this.amount1Out = amount1Out;
        }
    }

}
