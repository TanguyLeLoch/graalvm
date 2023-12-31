package com.natu.graalvm.domain.transaction.infrastructure;

import com.natu.graalvm.domain.transaction.core.model.EtherscanLogResponse;
import com.natu.graalvm.domain.transaction.core.model.EtherscanTxResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

@FeignClient(value = "etherscan", url = "${etherscan.url}")
public interface EtherscanClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api")
    EtherscanTxResponse getTransactions(URI baseUrl,
                                        @RequestParam("module") String module,
                                        @RequestParam("action") String action,
                                        @RequestParam("address") String address,
                                        @RequestParam("startblock") String startblock,
                                        @RequestParam("endblock") String endblock,
                                        @RequestParam("sort") String sort,
                                        @RequestParam("apikey") String apikey);

    @RequestMapping(method = RequestMethod.GET, value = "/api")
    EtherscanLogResponse getLogs(URI baseUrl,
                                 @RequestParam("module") String module,
                                 @RequestParam("action") String action,
                                 @RequestParam("address") String address,
                                 @RequestParam("startblock") String startblock,
                                 @RequestParam("endblock") String endblock,
                                 @RequestParam("topic0") String topic0,
                                 @RequestParam("apikey") String apikey);

}