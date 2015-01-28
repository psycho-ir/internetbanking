package com.samenea.payments.model;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.deposit.IDepositService;
import com.samenea.commons.component.model.BasicRepository;
import com.samenea.payments.model.banking.DefaultDepositService;
import com.samenea.payments.order.ProductSpec;
import com.samenea.payments.order.delivery.Delivery;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.mockito.Mockito.*;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
@ContextConfiguration(locations = { "classpath:context.xml","classpath:contexts/mock.xml" })
public class DefaultDepositChargeServiceTest extends AbstractJUnit4SpringContextTests{
    DefaultDepositService depositChargeService;
    @Autowired
    IDepositService depositService;
    @Autowired
    BasicRepository<Delivery,Long> productRepository;
    @Value("${banking.default.debitNumber}")
    private String debitNumber;

    @Value("${banking.depositDescriptionTemplate}")
    private String depositDescriptionTemplate;
    private String depositDescription;
    @Value("${banking.depositUserId}")
    private String userId;
    @Value("${banking.debitBranchCode}")
    private String debitBranchCode;
    String depositNumber = "100";

    Integer amount = 100;

//    private String criteria;
    ProductSpec productSpec;
    private String transactionId;
    private String orderId;

    @Before
    public void setUp() throws Exception {
        reset(depositService);
        depositChargeService = new DefaultDepositService();
        productSpec = DepositParams.fromParams(depositNumber,amount).getProductSpec();
        transactionId = "transactionId";
        orderId = "orderId";
        depositDescription = String.format(depositDescriptionTemplate,transactionId);
    }

    //region chargeDeposit tests
    @Test
    public void chargeDeposit_should_call_simia() throws ChargeException {
        logger.warn(depositDescriptionTemplate);
        depositChargeService.chargeDeposit(depositNumber,amount,debitNumber);
        verify(depositService).
                chargeDeposit(eq(amount), eq(debitNumber), eq(depositNumber), any(String.class), eq(userId), eq(debitBranchCode));
    }
    public void chargeDeposit_should_throw_simia_exception() throws ChargeException {
        doThrow(new ChargeException("1", "Mock Charge Exception")).when(depositService).
                chargeDeposit(amount, debitNumber, depositNumber, any(String.class), userId,debitBranchCode);
        depositChargeService.chargeDeposit(depositNumber,amount,debitNumber);
    }
    //endregion

    //region deliver tests
    @Test
    public void deliver_should_charge_corresponding_deposit() throws ChargeException {
        final String bankingTransactionId = "bankingTransactionId";
        when(depositService.chargeDeposit(amount, debitNumber, depositNumber, depositDescription, userId,debitBranchCode)).thenReturn(bankingTransactionId);

        when(productRepository.store(any(Delivery.class))).thenAnswer(new Answer<Delivery>() {
            @Override
            public Delivery answer(InvocationOnMock invocation) throws Throwable {
                return (Delivery) invocation.getArguments()[0];
            }
        });
        final Delivery delivery = depositChargeService.deliver(productSpec, orderId, transactionId,debitNumber);

        Assert.assertNotNull("product should not be null", delivery);
        Assert.assertEquals(bankingTransactionId, delivery.getSerialNumber());
        Assert.assertEquals(productSpec, delivery.getProductSpec());
        Assert.assertEquals(orderId, delivery.getOrderId());
        verify(depositService).chargeDeposit(amount, debitNumber, depositNumber, depositDescription, userId,debitBranchCode);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deliver_should_not_accept_invalid_criteria() throws ChargeException {
        final String criteria1 = String.format(" %s == %s and%s  == %s ",
                DepositParams.CREDIT_NUMBER_KEY, depositNumber, DepositParams.AMOUNT_KEY, amount);
        depositChargeService.deliver(new ProductSpec("Deposit", criteria1), orderId, transactionId,debitNumber);
    }
    @Test(expected = DeliveryException.class)
    public void deliver_should_throw_exception_if_charge_throws_exception() throws ChargeException {
        doThrow(new ChargeException("1", "Mock Charge Exception")).when(depositService).
                chargeDeposit(amount, debitNumber, depositNumber, depositDescription, userId,debitBranchCode);
        depositChargeService.deliver(productSpec, orderId, transactionId,debitNumber);
    }
    //endregion

    @Test(expected = ChargeException.class)
    public void probeChargeDeposit_should_throw_depositService_checkChargeFeasibilityException() throws ChargeException {
        doThrow(new ChargeException("1", "Mock Charge Exception")).when(depositService).
                checkChargingFeasibility(eq(amount), eq(debitNumber), eq(depositNumber), any(String.class), eq(userId),eq(debitBranchCode));
        depositChargeService.probeChargeDeposit(amount,depositNumber);
    }

}
