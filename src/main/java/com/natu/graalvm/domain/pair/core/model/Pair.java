package com.natu.graalvm.domain.pair.core.model;

import lombok.Getter;

@Getter
public class Pair {

    String pairAddress;
    Token token0;
    Token token1;

    public Pair(String pairAddress, Token token0, Token token1) {
        this.pairAddress = pairAddress;
        this.token0 = token0;
        this.token1 = token1;
    }

}
