package com.natu.graalvm.domain.pair.core.model;

import lombok.Getter;

@Getter
public class Token {
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