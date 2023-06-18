package com.natu.graalvm.domain.transaction.core.model;


import lombok.Getter;

import java.util.List;

@Getter
public class EtherscanResponse {
    int status;
    String message;
    List<EtherscanTx> result;
}
