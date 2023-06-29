package com.natu.graalvm.domain.user.core.model;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.user.core.port.outgoing.RetrievePair;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Document(collection = "users")
public class UserInfraMongo {
    @Id
    String address;
    String status;
    List<String> pairs;
    Map<String, Balance> balances;

    public UserInfraMongo(User user) {
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.pairs = user.getPairs().stream().map(Pair::getPairAddress).collect(Collectors.toList());
        this.balances = user.getBalances();
    }

    @PersistenceCreator
    public UserInfraMongo(String address, String status, List<String> pairs, Map<String, Balance> balances) {
        this.address = address;
        this.status = status;
        this.pairs = pairs;
        this.balances = balances;
    }

    public User toDomain(RetrievePair retrievePair) {
        List<Pair> pairs = this.pairs.stream().map(
                pairAddress -> retrievePair.findByAddress(pairAddress).orElseThrow(() -> new RuntimeException("Pair not found"))
        ).collect(Collectors.toList());
        return new User(address, status, pairs, balances);
    }

    public void addPair(String pairAddress) {
        this.pairs.add(pairAddress);
    }
}
