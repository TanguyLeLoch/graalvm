package com.natu.graalvm.domain.user.core.model;

import lombok.Getter;
import org.springframework.data.annotation.PersistenceCreator;

@Getter
public class PairInfraMongo {

    String pairAddress;
    Pair.Token token0;
    Pair.Token token1;

    @PersistenceCreator
    public PairInfraMongo(String pairAddress, Pair.Token token0, Pair.Token token1) {
        this.pairAddress = pairAddress;
        this.token0 = token0;
        this.token1 = token1;
    }

    public PairInfraMongo(Pair pair) {
        this.pairAddress = pair.getPairAddress();
        this.token0 = pair.getToken0();
        this.token1 = pair.getToken1();
    }

    Pair toDomain() {
        return new Pair(pairAddress, token0, token1);
    }
}
