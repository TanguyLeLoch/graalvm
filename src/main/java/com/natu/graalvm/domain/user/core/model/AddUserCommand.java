package com.natu.graalvm.domain.user.core.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddUserCommand {
    String address;
}
