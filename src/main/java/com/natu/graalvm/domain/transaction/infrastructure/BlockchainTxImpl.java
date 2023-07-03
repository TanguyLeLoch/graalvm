package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.common.Web3jApi;
import com.natu.graalvm.domain.transaction.core.model.Log;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.transaction.core.port.outgoing.Blockchain;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BlockchainTxImpl implements Blockchain {

    private final EtherscanApi etherscanApi;
    private final Web3jApi web3jApi;

    public BlockchainTxImpl(EtherscanApi etherscanApi, Web3jApi web3jApi) {
        this.etherscanApi = etherscanApi;
        this.web3jApi = web3jApi;
    }

    @Override
    public List<Transaction> getTransactions(String address) {
        return etherscanApi.getTransactions(address);
    }

    @Override
    public List<Log> getTransactionLogs(String txHash) {
        return web3jApi.getTransactionLogs(txHash);
    }
}
