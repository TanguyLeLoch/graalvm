package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

import java.math.BigInteger;
import java.util.List;

@Getter
public class EtherscanLog extends AbstractEtherscanResult {
    private String address;
    private List<String> topics;
    private String data;
    private String logIndex;
    private String transactionHash;


    public Log toDomain() {
        String dataHex = this.data.split("0x")[1];
        String amount0In = dataHex.substring(0, 64);
        String amount1In = dataHex.substring(64, 128);
        String amount0Out = dataHex.substring(128, 192);
        String amount1Out = dataHex.substring(192, 256);
        Log.Swap swap = new Log.Swap(hexToBigInteger(amount0In), hexToBigInteger(amount1In),
                hexToBigInteger(amount0Out), hexToBigInteger(amount1Out));
        return new Log(transactionHash, transactionIndex, address, topics, data, swap);
    }

    private BigInteger hexToBigInteger(String hex) {
        return new BigInteger(hex, 16);
    }
}
