package com.samenea.payments.web.model.loan;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Date: 2/12/13
 * Time: 10:44 AM
 *
 * @Author:payam
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =LoanNumberValidator.class )
@Documented
public @interface LoanNumber {
    String message() default "{isValid.constraints.loan}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
