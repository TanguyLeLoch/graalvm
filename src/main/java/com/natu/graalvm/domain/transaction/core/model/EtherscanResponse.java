package com.natu.graalvm.domain.transaction.core.model;


import lombok.Getter;

import java.util.List;

public abstract class EtherscanResponse<T extends AbstractEtherscanResult> {
    @Getter
    int status;
    @Getter
    String message;
    List<T> result;

}
