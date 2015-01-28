package com.samenea.payments.model.loan;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.loan.IInstallment;
import com.samenea.banking.loan.ILoan;
import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.banking.LoanService;
import com.samenea.payments.order.ProductSpec;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author: Jalal Ashrafi
 * Date: 2/12/13
 */
@ContextConfiguration(locations = {"classpath:context.xml","classpath:contexts/mock.xml"})
public class DefaultLoanOrderServiceTest extends AbstractJUnit4SpringContextTests{
    private static final Logger logger = LoggerFactory.getLogger(DefaultLoanOrderServiceTest.class);
    @Autowired
    private LoanService mockLoanService;
    @Autowired
    private ILoanService mockBankingLoanService;

    @Mock
    private ILoan mockedLoan;
    @Mock
    private IInstallment mockedInstallment;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        final String loanNumber = "loanNumber";
        final String installmentNumber = "installmentNumber";
        ICustomer borrower = new ICustomer() {
            @Override
            public String getName() {
                return "testFirstName";
            }

            @Override
            public String getLastName() {
                return "testLastName";
            }

            @Override
            public String getCustomerCode() {
                return "326598";
            }

            @Override
            public Boolean isUserLock() {
                return false;
            }
        };
        when(mockBankingLoanService.findLoan(loanNumber)).thenReturn(mockedLoan);
        when(mockBankingLoanService.findCustomer(loanNumber)).thenReturn(borrower);

        when(mockedLoan.getPayableInstallment()).thenReturn(mockedInstallment);
        when(mockedLoan.getLoanNumber()).thenReturn(loanNumber);

        when(mockedInstallment.getInstallmentNumber()).thenReturn(installmentNumber);
        when(mockedInstallment.getPayableAmount()).thenReturn(100L);
    }



    @Test
    public void createInstallmentPaySpec_should_create_correct_spec() throws Exception {
        final DefaultLoanOrderService defaultLoanOrderService = new DefaultLoanOrderService();
        ProductSpec loanInstallmentPaySpec = defaultLoanOrderService.createInstallmentPaySpec(mockedLoan.getLoanNumber());

        verify(mockLoanService).probePayInstallment(mockedLoan.getLoanNumber(), mockedInstallment.getInstallmentNumber(),mockedInstallment.getPayableAmount());
        final InstallmentParams installmentParams = InstallmentParams.fromParams(mockedLoan.getLoanNumber(), mockedInstallment.getInstallmentNumber(), mockedInstallment.getPayableAmount()) ;
        Assert.assertEquals(installmentParams.getProductSpec(), loanInstallmentPaySpec);
    }
}
