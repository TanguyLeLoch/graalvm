package com.natu.graalvm.domain.transaction.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddTransactionCommand {
    String hash;
}
