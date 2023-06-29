package com.natu.graalvm.domain.pair.core.port;

import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.port.incomming.AddNewPair;
import com.natu.graalvm.domain.pair.core.port.outgoing.Blockchain;
import com.natu.graalvm.domain.pair.core.port.outgoing.PairDatabase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class PairFacade implements AddNewPair {
    private final PairDatabase database;
    private final Blockchain blockchain;
    private static final Logger LOGGER = LoggerFactory.getLogger(PairFacade.class);

    public PairFacade(PairDatabase database, Blockchain blockchain) {
        this.database = database;
        this.blockchain = blockchain;
    }

    @Override
    public Pair handleGetOrCreate(String address) {
        return database.findByAddress(address).orElseGet(() -> createNewPair(address));
    }

    private Pair createNewPair(String address) {
        Pair pair = blockchain.getPair(address).orElseThrow(
                () -> new NotFoundException("Pair not found in blockchain")
        );

        return database.insert(pair);
    }

}
