package com.samenea.payments.model;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.ProductSpec;
import org.junit.Test;
import org.slf4j.Logger;

import static junit.framework.Assert.assertEquals;

/**
 * @author: Jalal Ashrafi
 * Date: 1/28/13
 */
public class DepositParamsTest {
    private static final Logger logger = LoggerFactory.getLogger(DepositParamsTest.class);
    private final String creditNumber = "DN-1234";
    private final int amount = 100;
    @Test(expected = IllegalArgumentException.class)
    public void fromCriteria_should_should_not_accept_maplformed_criteria(){
        final String malformed = String.format(" %s == %s and%s  == %s ",
                DepositParams.CREDIT_NUMBER_KEY, creditNumber, DepositParams.AMOUNT_KEY, amount);
        DepositParams.fromCriteriaString(malformed);
    }
    @Test
    public void fromCriteria_should_create_correct_params(){
        final DepositParams depositParams = DepositParams.fromCriteriaString(String.format(" %s == %s and %s  == %s ",
                DepositParams.CREDIT_NUMBER_KEY, creditNumber, DepositParams.AMOUNT_KEY, amount));
        assertEquals(creditNumber, depositParams.getDepositNumber());
        assertEquals(amount, depositParams.getAmount());
        final String expectedCriteriaString = String.format("%s == %s and %s == %s",
                DepositParams.CREDIT_NUMBER_KEY, creditNumber, DepositParams.AMOUNT_KEY, amount);
        assertEquals(depositParams.getProductSpec(), new ProductSpec(DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME, expectedCriteriaString));
    }
    @Test
    public void fromParameters_should_create_correct_params(){
        final DepositParams depositParams = DepositParams.fromParams(creditNumber, amount);
        assertEquals(creditNumber, depositParams.getDepositNumber());
        assertEquals(amount, depositParams.getAmount());
        final String expectedCriteriaString = String.format("%s == %s and %s == %s",
                DepositParams.CREDIT_NUMBER_KEY, creditNumber, DepositParams.AMOUNT_KEY, amount);
        assertEquals(depositParams.getProductSpec(), new ProductSpec(DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME, expectedCriteriaString));
    }

}
