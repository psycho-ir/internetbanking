package com.samenea.payments.web.model.deposit;

import com.samenea.banking.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Date: 1/26/13
 * Time: 2:16 PM
 *
 * @Author:payam
 */

@Configurable
public class DepositNumberValidator implements ConstraintValidator<DepositNumber, String> {

    @Autowired
    IDepositService depositService;

    @Override
    public void initialize(DepositNumber depositNumber) {

    }
    @Override
    public boolean isValid(String depositNumber, ConstraintValidatorContext constraintValidatorContext) {
        try{
              if(depositNumber.length()>2)
               return depositService.isValidForCharging(depositNumber);
        }
        catch (com.samenea.commons.component.model.exceptions.NotFoundException excption){
            return  false;
        }
        return  false;
    }
}

