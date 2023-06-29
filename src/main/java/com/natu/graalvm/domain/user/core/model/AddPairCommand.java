package com.natu.graalvm.domain.user.core.model;

import com.natu.graalvm.domain.pair.core.model.Pair;

public class AddPairCommand {

    String pairAddress;
    String token0;
    String token1;

    public AddPairCommand(String pairAddress, String token0, String token1) {
        this.pairAddress = pairAddress.toLowerCase();
        token0 = token0.toLowerCase();
        token1 = token1.toLowerCase();
        if (token0.compareTo(token1) > 0) {
            this.token0 = token1;
            this.token1 = token0;
        } else {
            this.token0 = token0;
            this.token1 = token1;
        }

    }

    public Pair toPair() {
        return new Pair(pairAddress, token0, token1);
    }

    public String getPairAddress() {
        return pairAddress;
    }

    public String getToken0() {
        return token0;
    }

    public String getToken1() {
        return token1;
    }
}
