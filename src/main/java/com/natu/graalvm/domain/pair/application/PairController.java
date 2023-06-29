package com.natu.graalvm.domain.pair.application;

import com.natu.graalvm.domain.pair.core.model.Pair;
import com.natu.graalvm.domain.pair.core.port.incomming.AddNewPair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pairs")
@Slf4j
public class PairController {


    private final AddNewPair addNewPair;

    public PairController(AddNewPair addNewPair) {
        this.addNewPair = addNewPair;

    }

    @PostMapping("/{address}")
    public Pair getOrCreate(@PathVariable String address) {
        return addNewPair.handleGetOrCreate(address);
    }


    @ExceptionHandler(Exception.class)
    public String handleError(Exception ex) throws Exception {
        log.error("Error: ", ex);
        throw ex;
    }
}
