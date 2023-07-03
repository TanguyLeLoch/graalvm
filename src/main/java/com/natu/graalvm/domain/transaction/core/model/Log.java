package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

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

    public static final class Swap {
        private final BigInteger amount0In;
        private final BigInteger amount1In;
        private final BigInteger amount0Out;
        private final BigInteger amount1Out;

        @PersistenceCreator
        public Swap(BigInteger amount0In, BigInteger amount1In, BigInteger amount0Out, BigInteger amount1Out) {
            this.amount0In = amount0In;
            this.amount1In = amount1In;
            this.amount0Out = amount0Out;
            this.amount1Out = amount1Out;
        }

        public BigInteger amount0In() {
            return amount0In;
        }

        public BigInteger amount1In() {
            return amount1In;
        }

        public BigInteger amount0Out() {
            return amount0Out;
        }

        public BigInteger amount1Out() {
            return amount1Out;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Swap) obj;
            return Objects.equals(this.amount0In, that.amount0In) &&
                    Objects.equals(this.amount1In, that.amount1In) &&
                    Objects.equals(this.amount0Out, that.amount0Out) &&
                    Objects.equals(this.amount1Out, that.amount1Out);
        }

        @Override
        public int hashCode() {
            return Objects.hash(amount0In, amount1In, amount0Out, amount1Out);
        }

        @Override
        public String toString() {
            return "Swap[" +
                    "amount0In=" + amount0In + ", " +
                    "amount1In=" + amount1In + ", " +
                    "amount0Out=" + amount0Out + ", " +
                    "amount1Out=" + amount1Out + ']';
        }


    }

}
