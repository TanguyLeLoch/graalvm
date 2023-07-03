package com.natu.graalvm.domain.user.application;

import com.natu.graalvm.domain.common.exception.NotFoundException;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.transaction.application.TransactionService;
import com.natu.graalvm.domain.transaction.core.model.Transaction;
import com.natu.graalvm.domain.user.core.model.Balance;
import com.natu.graalvm.domain.user.core.model.User;
import com.natu.graalvm.domain.user.core.port.incomming.AlterUser;
import com.natu.graalvm.domain.user.core.port.incomming.RetrieveUser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService {

    private final RetrieveUser retrieveUser;
    private final TransactionService transactionService;

    private final AlterUser alterUser;

    UserService(TransactionService transactionService, RetrieveUser retrieveUser, AlterUser alterUser) {
        this.transactionService = transactionService;
        this.retrieveUser = retrieveUser;
        this.alterUser = alterUser;
    }


    public Map<String, BigDecimal> computePnl(String userAddress) {
        User user = retrieveUser.handle(userAddress);
        List<Transaction> swapTxs = transactionService.retrieveSwapTxs(userAddress);
        user.addSwapTransactions(swapTxs);
        User savedUser = alterUser.update(user);

        Map<String, BigDecimal> pnl = new HashMap<>();
        for (Map.Entry<String, Balance> entry : savedUser.getBalances().entrySet()) {
            // convert to BigDecimal to avoid overflow
            BigDecimal balance = new BigDecimal(entry.getValue().getBalance());
            BigDecimal inDecimal = balance.divide(new BigDecimal(10).pow(18));
            String symbol = findSymbol(entry.getKey(), user.getPairs());

            pnl.put(symbol, inDecimal);
        }


        return pnl;
    }

    private String findSymbol(String contract, List<Pair> pairs) {
        for (Pair pair : pairs) {
            if (pair.getToken0().getAddress().equals(contract)) {
                return pair.getToken0().getSymbol();
            } else if (pair.getToken1().getAddress().equals(contract)) {
                return pair.getToken1().getSymbol();
            }
        }
        throw new NotFoundException("Symbol not found");
    }

    public void fetchTransactions(String userAddress) {
        transactionService.addNewTransactionFromBlockchain(userAddress);
    }

    public void addLogToTransaction(String userAddress) {
        transactionService.addLogToUserTransactions(userAddress);
    }

    public void addPairToUser(String userAddress) {
        Set<String> pairs = transactionService.retrievePairs(userAddress);
        alterUser.addPairs(userAddress, pairs);
    }
}
