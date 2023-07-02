package com.natu.graalvm.domain.user.core.model;

import com.natu.graalvm.domain.pair.application.EthAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AddPairCommand {

    @EthAddress
    private String pairAddress;

}
