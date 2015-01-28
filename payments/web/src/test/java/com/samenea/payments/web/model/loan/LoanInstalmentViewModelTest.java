package com.samenea.payments.web.model.loan;

import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.model.exceptions.NotFoundException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Date: 2/12/13
 * Time: 11:42 AM
 *
 * @Author:payam
 */
public class LoanInstalmentViewModelTest {
    @Mock
    ILoanService loanService;
    LoanInstalmentViewModel loanInstalmentViewModel;
    private String LOAN_NUMBER="20200";
    private static Validator	validator;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        loanInstalmentViewModel=new LoanInstalmentViewModel(LOAN_NUMBER,"info@samenea.com","09373392831");
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Test
    @Ignore
    public void loanNumber_should_return_false_when_service_throw_NotFound_exception(){
        doThrow(new NotFoundException("not found....", null)).when(loanService).findLoan(LOAN_NUMBER);
        Set<ConstraintViolation<LoanInstalmentViewModel>> constraintViolations  = validator.validate(loanInstalmentViewModel);
        Assert.assertEquals("{isValid.constraints.loan}",constraintViolations.iterator().next().getMessage());
    }
    @Test
    @Ignore
    public void not_allow_email_when_email_is_null(){
        loanInstalmentViewModel.setEmail(null);
        Set<ConstraintViolation<LoanInstalmentViewModel>> constraintViolations  = validator.validate(loanInstalmentViewModel);
        Assert.assertEquals(2,constraintViolations.size());
    }
    @Test
    @Ignore
    public void should_be_return_custom_message_when_loan_number_is_not_valid_for_payment(){
        when(loanService.isValidForPayment(LOAN_NUMBER)).thenReturn(false);
        Set<ConstraintViolation<LoanInstalmentViewModel>> constraintViolations  = validator.validate(loanInstalmentViewModel);
        Assert.assertEquals("{isNotValid.loanNumber}", constraintViolations.iterator().next().getMessage());

    }

}
