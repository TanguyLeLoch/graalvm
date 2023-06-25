package com.natu.graalvm.domain.transaction.core.model;


import java.util.List;

public class EtherscanLogResponse extends EtherscanResponse<AbstractEtherscanResult> {

    List<EtherscanLog> result;

    public List<EtherscanLog> getResult() {
        return result;
    }

}
