package com.natu.graalvm.it.user;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.user.core.model.Balance;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class UserResponseTest {
    String address;
    String status;

    List<Pair> pairs;
    Map<String, Balance> balances;
}
