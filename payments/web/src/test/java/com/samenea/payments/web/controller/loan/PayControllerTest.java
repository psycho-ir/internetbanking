package com.samenea.payments.web.controller.loan;

import com.samenea.payments.web.model.View;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

/**
 * Date: 2/12/13
 * Time: 11:27 AM
 *
 * @Author:payam
 */
public class PayControllerTest {
    @Mock
    ModelMap modelMap;
    @InjectMocks
    private PayController payController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void should_return_to_loan_instalment_page(){
        final  String resultPage= payController.displayInstalmentPage(modelMap);
        Assert.assertEquals(View.Loan.LOAN_PAY, resultPage);
    }
}
