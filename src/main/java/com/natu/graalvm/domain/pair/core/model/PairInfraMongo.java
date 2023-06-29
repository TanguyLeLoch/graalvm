package com.natu.graalvm.domain.pair.core.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "pairs")
public class PairInfraMongo {

    @Id
    String pairAddress;
    Token token0;
    Token token1;

    @PersistenceCreator
    public PairInfraMongo(String pairAddress, Token token0, Token token1) {
        this.pairAddress = pairAddress;
        this.token0 = token0;
        this.token1 = token1;
    }

    public PairInfraMongo(Pair pair) {
        this.pairAddress = pair.getPairAddress();
        this.token0 = pair.getToken0();
        this.token1 = pair.getToken1();
    }

    public Pair toDomain() {
        return new Pair(pairAddress, token0, token1);
    }
}
