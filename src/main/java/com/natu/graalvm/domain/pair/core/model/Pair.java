package com.natu.graalvm.domain.pair.core.model;

import lombok.Getter;

@Getter
public class Pair {

    String pairAddress;
    Token token0;
    Token token1;

    public Pair(String pairAddress, String token0, String token1) {
        this.pairAddress = pairAddress;
        this.token0 = new Token(token0, "", "", 18);
        this.token1 = new Token(token1, "", "", 18);
    }

    public Pair(String pairAddress, Token token0, Token token1) {
        this.pairAddress = pairAddress;
        this.token0 = token0;
        this.token1 = token1;
    }

    @Getter
    public static class Token {
        String address;
        String symbol;
        String name;
        int decimals;


        public Token(String address, String symbol, String name, int decimals) {
            this.address = address;
            this.symbol = symbol;
            this.name = name;
            this.decimals = decimals;
        }
    }

}
