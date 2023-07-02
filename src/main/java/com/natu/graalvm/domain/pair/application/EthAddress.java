package com.natu.graalvm.domain.pair.application;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, java.lang.annotation.ElementType.PARAMETER})
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EthAddressValidator.class})
public @interface EthAddress {


}
