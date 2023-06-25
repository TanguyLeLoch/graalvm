package com.natu.graalvm.domain.transaction.core.model;

import lombok.Getter;

@Getter
public abstract class AbstractEtherscanResult {

    String blockNumber;
    String timeStamp;
    String blockHash;
    String transactionIndex;
    String gasPrice;
    String gasUsed;
}
