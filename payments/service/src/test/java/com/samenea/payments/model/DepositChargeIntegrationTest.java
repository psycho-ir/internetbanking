package com.samenea.payments.model;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.deposit.IDeposit;
import com.samenea.banking.deposit.IDepositService;
import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.banking.DepositChargeService;
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
@ContextConfiguration(locations = {"classpath*:context.xml"})
public class DepositChargeIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Value("${banking.default.debitNumber}")
    private String defaultDebitNumber;
    private final String credit = "3461215593.58";
    @Autowired
    DepositChargeService depositChargeService;
    @Autowired
    IDepositService bankingDepositService;
    @Autowired
    LoanService loanService;
    private static final Logger logger = LoggerFactory.getLogger(DepositChargeIntegrationTest.class);
    private Integer amount = 100;

    @Ignore
    @Test
    public void deliver_should_charge_deposit() throws ChargeException {
        final IDeposit before = bankingDepositService.findDeposit(credit);
        logger.info("************************ Before: {}", before.getRemainedAmount());
        depositChargeService.chargeDeposit(credit, amount, defaultDebitNumber);
        final IDeposit after = bankingDepositService.findDeposit(credit);
        logger.info("************************ After: {}", after.getRemainedAmount());
        Assert.assertEquals(before.getRemainedAmount() + amount, after.getRemainedAmount().intValue());
    }

    @Ignore

    @Test
    public void probeChargeDeposit_should_has_no_effect_on_deposit() throws ChargeException {
        final IDeposit before = bankingDepositService.findDeposit(credit);
        depositChargeService.probeChargeDeposit(amount, credit);
        final IDeposit after = bankingDepositService.findDeposit(credit);
        Assert.assertEquals(before.getRemainedAmount().intValue(), after.getRemainedAmount().intValue());
    }
}
