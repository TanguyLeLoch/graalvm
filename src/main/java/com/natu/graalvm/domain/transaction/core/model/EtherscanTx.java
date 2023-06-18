package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

@Getter
public class EtherscanTx {

    String blockNumber;
    String timeStamp;
    String hash;
    String nonce;
    String blockHash;
    String transactionIndex;
    String from;
    String to;
    String value;
    String gas;
    String gasPrice;
    String isError;
    String txreceipt_status;
    String input;
    String contractAddress;
    String cumulativeGasUsed;
    String gasUsed;
    String confirmations;
    String methodId;
    String functionName;


    public Transaction toDomain() {
        return new Transaction(this.hash, this.from, this.to, Long.parseLong(this.blockNumber));
    }
}
