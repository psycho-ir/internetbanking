package com.samenea.payments.web.model.deposit;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Date: 1/26/13
 * Time: 2:21 PM
 *
 * @Author:payam
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =DepositNumberValidator.class )
@Documented
public @interface DepositNumber {
    String message() default "{isValid.constraints.deposit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
