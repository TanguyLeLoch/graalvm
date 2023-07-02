package com.natu.graalvm.domain.pair.application;

import com.natu.graalvm.domain.common.exception.FunctionalException;
import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.port.incomming.AddNewPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping("pairs")
@Slf4j
public class PairController {


    private final AddNewPair addNewPair;

    public PairController(AddNewPair addNewPair) {
        this.addNewPair = addNewPair;

    }


    @PostMapping("/{address}")
    public Pair getOrCreate(@PathVariable @EthAddress() String address) {
        if (!EthAddressValidator.isValid(address)) {
            throw new FunctionalException("Invalid address");
        }
        return addNewPair.handleGetOrCreate(address.toLowerCase());
    }


    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex) throws Exception {
        log.error("Error: ", ex);
        throw ex;
    }
}
