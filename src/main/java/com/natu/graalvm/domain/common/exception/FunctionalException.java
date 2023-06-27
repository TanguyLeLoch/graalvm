package com.natu.graalvm.domain.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Functional exception")
public class FunctionalException extends RuntimeException {

    public FunctionalException(String message, Object... args) {
        super(String.format(message, args));
    }
}