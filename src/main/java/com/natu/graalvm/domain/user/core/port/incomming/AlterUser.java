package com.natu.graalvm.domain.user.core.port.incomming;

import com.natu.graalvm.domain.user.core.model.AddPairCommand;
import com.natu.graalvm.domain.user.core.model.User;

public interface AlterUser {
    void addPair(String userAddress, AddPairCommand command);

    User update(User user);
}
