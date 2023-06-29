package com.natu.graalvm.domain.user.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class AddPairCommand {

    private String pairAddress;

}
