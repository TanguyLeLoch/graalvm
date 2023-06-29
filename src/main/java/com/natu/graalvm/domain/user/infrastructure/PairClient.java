package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.pair.core.model.Pair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;


@FeignClient(value = "pairService", url = "http://localhost:8081")
public interface PairClient {

    @RequestMapping(method = RequestMethod.POST, value = "/pairs/{address}")
    Pair findByAddress(URI baseUrl, @PathVariable String address);
}
