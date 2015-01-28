package com.samenea.payments.web.model.loan;

import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.model.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Date: 2/12/13
 * Time: 10:47 AM
 *
 * @Author:payam
 */
@Configurable
public class LoanNumberValidator implements ConstraintValidator<LoanNumber, String> {
    @Override
    public void initialize(LoanNumber loanNumber) {
    }
    @Value("${isNotValid.loanNumber}")
    private String isNotValidLoanNumberMessage;
    @Value("${notFound.loanNumber}")
    private String notFoundLoanNumberMessage;
    @Autowired
    ILoanService loanService;

    @Override
    public boolean isValid(String loanNumber, ConstraintValidatorContext constraintValidatorContext) {

        try {
            if (loanNumber.length() > 0) {
                if (loanService.isValidForPayment(loanNumber)) {
                    return true;
                } else {
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate(isNotValidLoanNumberMessage).addConstraintViolation();
                }
            }
        } catch (NotFoundException exception) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(notFoundLoanNumberMessage).addConstraintViolation();
            return false;
        }
        return false;
    }
}
