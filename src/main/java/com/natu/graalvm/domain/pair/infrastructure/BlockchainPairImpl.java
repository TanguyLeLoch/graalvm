package com.natu.graalvm.domain.pair.infrastructure;

import com.natu.graalvm.domain.common.Web3jApi;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.port.outgoing.Blockchain;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BlockchainPairImpl implements Blockchain {

    private final Web3jApi web3jApi;

    public BlockchainPairImpl(Web3jApi web3jApi) {
        this.web3jApi = web3jApi;
    }

    @Override
    public Optional<Pair> getPair(String address) {
        return web3jApi.getPair(address);
    }
}
