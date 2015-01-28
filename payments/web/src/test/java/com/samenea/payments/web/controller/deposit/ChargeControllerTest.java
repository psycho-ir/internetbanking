package com.samenea.payments.web.controller.deposit;

import com.samenea.payments.web.model.View;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

/**
 * Date: 2/3/13
 * Time: 12:21 PM
 *
 * @Author:payam
 */
public class ChargeControllerTest {
    @Mock
    ModelMap modelMap;
    @InjectMocks
    private ChargeController chargeController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void should_return_list_customer_when_depositNumber_is_valid(){

        final  String resultPage= chargeController.displayChargePage(modelMap);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }


}

