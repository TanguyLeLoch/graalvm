package com.natu.graalvm.domain.pair.application;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class EthAddressValidator implements ConstraintValidator<EthAddress, String> {

    @Override
    public boolean isValid(String ethAddress, ConstraintValidatorContext constraintValidatorContext) {
        return isValid(ethAddress);
    }

    public static boolean isValid(String ethAddress) {
        if (ethAddress == null) {
            log.info(" null address is not valid");
            return false;
        }
        log.info(" {} address is valid: {}", ethAddress, ethAddress.matches("^0x[a-fA-F0-9]{40}$"));
        return ethAddress.matches("^0x[a-fA-F0-9]{40}$");
    }
}
