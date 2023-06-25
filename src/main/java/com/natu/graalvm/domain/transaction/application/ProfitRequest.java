package com.natu.graalvm.domain.transaction.application;

public record ProfitRequest(String address, String contractTokenIn, String contractTokenOut, String pair) {
}
