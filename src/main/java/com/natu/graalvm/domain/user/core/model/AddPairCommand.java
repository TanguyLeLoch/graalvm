package com.natu.graalvm.domain.user.core.model;

public class AddPairCommand {

    String pairAddress;

    public AddPairCommand(String pairAddress) {
        this.pairAddress = pairAddress;
    }

    public String getPairAddress() {
        return pairAddress;
    }

}
