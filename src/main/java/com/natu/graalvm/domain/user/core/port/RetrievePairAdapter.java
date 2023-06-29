package com.natu.graalvm.domain.user.core.port;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.user.core.port.outgoing.RetrievePair;
import com.natu.graalvm.domain.user.infrastructure.PairClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Component
public class RetrievePairAdapter implements RetrievePair {


    PairClient pairClient;
    URI baseUrl;

    public RetrievePairAdapter(PairClient pairClient, @Value("${server.port}") String port) {
        this.pairClient = pairClient;
        this.baseUrl = URI.create("http://localhost:" + port);
    }

    @Override
    public Optional<Pair> findByAddress(String address) {
        Pair Response = pairClient.findByAddress(baseUrl, Map.of("pairAddress", address));
        return Optional.ofNullable(Response);
    }
}
