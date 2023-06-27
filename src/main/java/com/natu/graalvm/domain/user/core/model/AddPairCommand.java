package com.natu.graalvm.domain.user.core.model;

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
}
