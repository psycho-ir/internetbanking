package com.samenea;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.deposit.IDeposit;
import com.samenea.banking.deposit.IDepositService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.DepositOrderService;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.Receipt;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static junit.framework.Assert.assertEquals;


@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@ContextConfiguration(locations = { "classpath:context.xml" })
@Ignore
public class OrderIntegrationTest extends AbstractJUnit4SpringContextTests{
    private final String creditNumber = "3152216737.58";
    private static final Logger logger = LoggerFactory.getLogger(OrderIntegrationTest.class);
    private int amount = 100;
    @Autowired
    private DepositOrderService depositOrderService;
    @Autowired
    IDepositService bankingDepositService;
    private CustomerInfo customerInfo = new CustomerInfo("91211212121", "a@gmail.com");

    //ignored temporarily
    @Ignore
    @Test
    public void deliver_for_chargeAccount_Items_should_charge_corresponding_deposites() throws ChargeException, InterruptedException {


        final Order order = depositOrderService.createCheckedOutDepositChargeOrder(creditNumber, amount, customerInfo );
        final Receipt receipt = new Receipt("TRN-101", "mellat");
        order.deliver(receipt);

    }
}
