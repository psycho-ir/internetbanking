package com.samenea.payments.web.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/17/13
 * Time: 8:48 AM
 */
@Configurable
public class ValidatorWrapper implements Validator {
    private List<Validator> validators;

    @Autowired
    LocalValidatorFactoryBean v;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        for (Validator validator : validators) {
            if (validator.supports(target.getClass())) {
                validator.validate(target, errors);
            }
        }
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }
}
