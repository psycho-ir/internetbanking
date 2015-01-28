package com.samenea.payments.web.controller.loan;

import com.samenea.banking.loan.IInstallment;
import com.samenea.banking.loan.ILoan;
import com.samenea.banking.loan.ILoanService;
import com.samenea.banking.loan.InstallmentStatus;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.common.CustomTimeValidator;
import com.samenea.payments.web.model.loan.LoanInstalmentViewModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Date: 2/16/13
 * Time: 1:25 PM
 *
 * @Author:payam
 */
@Configurable
public class ConfirmControllerTest {
    @Mock
    CustomTimeValidator customTimeValidator;
    @Mock
    ModelMap modelMap;
    @Spy
    @InjectMocks
    private ConfirmController confirmController;
    @Mock
    BindingResult result;
    @Mock
    ILoanService loanService;
    @Mock
    ILoan loan;
    @Mock
    IInstallment payInstallment;
    IInstallment installment;
    LoanInstalmentViewModel loanInstalmentViewModel;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        loanInstalmentViewModel=new LoanInstalmentViewModel("2020","info@test.com","09373392831");
        when(loan.getLoanNumber()).thenReturn("2020");
        when(loan.getOriginalAmount()).thenReturn(200L);
        when(payInstallment.getPayableAmount()).thenReturn(2020L);
        when(payInstallment.getInstallmentNumber()).thenReturn("2020");
//        when(payInstallment.getStatus()).thenReturn(InstallmentStatus.NOT_PAYED);
        List list=new ArrayList();
        list.add(payInstallment);
        when(customTimeValidator.isValid("10:22","22:55")) .thenReturn(true);


    }


    @Test
    public void should_be_return_to_loan_installments_page_when_loanInstalmentViewModel_has_error(){
        when(result.hasErrors()).thenReturn(true);
        final String resultPage =  confirmController.showConfirmLoanInstalment(modelMap,loanInstalmentViewModel,result);
        Assert.assertEquals(View.Loan.LOAN_PAY,resultPage);
    }
    @Test
    public void should_be_return_to_confirm_loan_installments_page_when_loanInstalmentViewModel_has_no_error(){
        when(result.hasErrors()).thenReturn(false);
        when(loanService.findLoan("2020")).thenReturn(loan);
        final String resultPage =  confirmController.showConfirmLoanInstalment(modelMap,loanInstalmentViewModel,result);
        Assert.assertEquals(View.Loan.LOAN_PAY,resultPage);
    }
}
