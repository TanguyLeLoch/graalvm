package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

@Getter
public class EtherscanTx extends AbstractEtherscanResult {

    String hash;
    String nonce;
    String from;
    String to;
    String value;
    String gas;
    String isError;
    String txreceipt_status;
    String input;
    String contractAddress;
    String cumulativeGasUsed;
    String confirmations;
    String methodId;
    String functionName;


    public Transaction toDomain() {
        Transaction tx = new Transaction(this.hash, this.from, this.to, Long.parseLong(this.blockNumber), this.transactionIndex);
        tx.fetch();
        return tx;
    }
}
