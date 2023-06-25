package com.natu.graalvm.domain.transaction.core.model;


import java.util.List;

public class EtherscanTxResponse extends EtherscanResponse<AbstractEtherscanResult> {
    List<EtherscanTx> result;

    public List<EtherscanTx> getResult() {
        return result;
    }
}
