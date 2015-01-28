package com.samenea.payments.model;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.deposit.IDeposit;
import com.samenea.banking.loan.IInstallment;
import com.samenea.banking.loan.ILoan;
import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.banking.LoanService;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * @author: Jalal Ashrafi
 * Date: 1/31/13
 */
@Ignore
@ContextConfiguration(locations = { "classpath:context.xml" })
public class LoanIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests{
    private final String loanNumber = "0084901600046";
    @Autowired
    LoanService loanService;
    @Value("banking.default.debitNumber")
    private String defaultDebitNumber;
    @Autowired
    ILoanService bankingLoanService;
    private static final Logger logger = LoggerFactory.getLogger(LoanIntegrationTest.class);

    //TODO currently no payable installment exist for this loan
    @Ignore
    @Test
    public void payInstallment_should_decrease_loan_amount()  {
        final ILoan before = bankingLoanService.findLoan(loanNumber);
        final IInstallment payableInstallment = before.getPayableInstallment();
        logger.info("************************ Before: {}",before.getRemainedAmount());
        final Long payableAmount = payableInstallment.getPayableAmount();
        loanService.payInstallment(loanNumber, payableInstallment.getInstallmentNumber(), payableAmount,defaultDebitNumber);
        final ILoan after = bankingLoanService.findLoan(loanNumber);
        logger.info("************************  After: {}",after.getRemainedAmount());
        Assert.assertEquals(before.getRemainedAmount().intValue() - payableAmount  , after.getRemainedAmount().intValue());
    }

}
