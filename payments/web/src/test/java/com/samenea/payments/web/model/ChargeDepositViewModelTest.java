package com.samenea.payments.web.model;

/**
 * Date: 1/26/13
 * Time: 4:31 PM
 *
 * @Author:payam
 */

import com.samenea.payments.web.model.deposit.ChargeDepositViewModel;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ChargeDepositViewModelTest {
    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Ignore
    public void depositNumberIsNull() {
        ChargeDepositViewModel chargeDepositViewModel = new ChargeDepositViewModel();
        Set<ConstraintViolation<ChargeDepositViewModel>> constraintViolations =
                validator.validate(chargeDepositViewModel);
        chargeDepositViewModel.setAmount("1111");
        chargeDepositViewModel.setDepositNumber1("1111");
        chargeDepositViewModel.setDescription("1111");
        chargeDepositViewModel.setEmail("test@test.com");
        assertEquals(1, constraintViolations.size());

    }

    @Test
    public void viewMdodel_shoud_return_integer_number_for_amount() {
        ChargeDepositViewModel chargeDepositViewModel = new ChargeDepositViewModel();
        chargeDepositViewModel.setAmount("۱,000");

        assertEquals("1000", chargeDepositViewModel.getAmount());
        chargeDepositViewModel.setAmount("۱,,,۰۰,۰");
        assertEquals("1000", chargeDepositViewModel.getAmount());

    }

    @Test
    public void viewMdodel_shoud_return_null_when_amount_is_null_number_for_amount() {
        ChargeDepositViewModel chargeDepositViewModel = new ChargeDepositViewModel();

        assertEquals(null, chargeDepositViewModel.getAmount());

    }


}
