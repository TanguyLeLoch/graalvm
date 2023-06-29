package com.natu.graalvm.domain.user.infrastructure;

import com.natu.graalvm.domain.pair.core.model.Pair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.util.Map;


@FeignClient(value = "pairService", url = "http://localhost:8081")
public interface PairClient {

    @RequestMapping(method = RequestMethod.POST, value = "/pairs")
    Pair findByAddress(URI baseUrl, @RequestBody Map<String, String> request);
}
