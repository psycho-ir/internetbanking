package com.samenea.payments.web.controller.help;

import com.samenea.payments.web.controller.deposit.ChargeController;
import com.samenea.payments.web.model.View;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Date: 3/27/13
 * Time: 10:17 AM
 *
 * @Author:payam
 */
public class HelpControllerTest {
    @InjectMocks
    private HelpController helpController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void should_be_return_to_help_deposit_page(){
        final  String resultPage= helpController.deposit();
        Assert.assertEquals(View.Help.DEPOSIT, resultPage);
   }
    @Test
    public void should_be_return_to_help_loan_page(){
        final  String resultPage= helpController.loan();
        Assert.assertEquals(View.Help.LOAN, resultPage);
    }
    @Test
    public void should_be_return_to_help_tracking_page(){
        final  String resultPage= helpController.tracking();
        Assert.assertEquals(View.Help.TRACKING, resultPage);
    }
}

