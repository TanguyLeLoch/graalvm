package com.natu.graalvm.domain.transaction.core.port.incomming;

import com.natu.graalvm.domain.transaction.core.model.AddTransactionCommand;

public interface AddNewTransaction {
    void fromCommand(AddTransactionCommand command);

    void newFromBlockchain(String address);
}
