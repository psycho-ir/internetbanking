package com.samenea.payments.model.banking;

import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.DepositParams;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.ProductSpec;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author: Jalal Ashrafi
 * Date: 2/13/13
 */
@ContextConfiguration(locations = {"classpath:context.xml", "classpath:contexts/mock.xml"})
public class DefaultLoanServiceTest extends AbstractJUnit4SpringContextTests {
    @Value("${banking.default.debitNumber}")
    private String debitNumber;

    @Value("${banking.depositDescriptionTemplate}")
    private String depositDescriptionTemplate;
    @Value("${banking.depositUserId}")
    private String userId;

    @Autowired
    private ILoanService mockedBankingLoanService;
    private static final Logger logger = LoggerFactory.getLogger(DefaultLoanServiceTest.class);

    private DefaultLoanService defaultLoanService;
    private String loanNumber;
    private String installmentNumber;
    private long amount;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(mockedBankingLoanService);
        defaultLoanService = new DefaultLoanService();
        loanNumber = "loanNumber";
        installmentNumber = "installmentNumber";
        amount = 100;
    }

    @Test
    public void payInstallment_should_call_banking() {

        defaultLoanService.payInstallment(loanNumber, installmentNumber, amount, debitNumber);
        verify(mockedBankingLoanService).payInstallment(userId, debitNumber, loanNumber, installmentNumber, amount);
    }

    @Test
    public void probePayInstallment_should_call_banking() {
        defaultLoanService.probePayInstallment(loanNumber, installmentNumber, amount);
        verify(mockedBankingLoanService).checkPaymentFeasibility(userId, debitNumber, loanNumber, installmentNumber, amount);
    }

    @Test
    public void deliver_should_throw_DeliveryException_when_banking_throws_exception() {
//        defaultLoanService.deliver(new );
    }

}
