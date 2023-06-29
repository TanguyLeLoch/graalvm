package com.natu.graalvm.domain.user.core.model;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.model.PairInfraMongo;
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
    List<PairInfraMongo> pairs;
    Map<String, Balance> balances;

    public UserInfraMongo(User user) {
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.pairs = user.getPairs().stream().map(PairInfraMongo::new).toList();
        this.balances = user.getBalances();
    }

    @PersistenceCreator
    public UserInfraMongo(String address, String status, List<PairInfraMongo> pairs, Map<String, Balance> balances) {
        this.address = address;
        this.status = status;
        this.pairs = pairs;
        this.balances = balances;
    }

    public User toDomain() {
        List<Pair> pairs = this.pairs.stream().map(PairInfraMongo::toDomain).collect(Collectors.toList());
        return new User(address, status, pairs, balances);
    }
}
