package com.natu.graalvm.it.transaction;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TransactionResponseTest {

    String hash;

    public String getHash() {
        return this.hash;
    }
}
