package com.samenea.payments.model;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.customer.ICustomerService;
import com.samenea.banking.deposit.ChargeException;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.idgenerator.service.IDGenerator;
import com.samenea.commons.idgenerator.service.IDGeneratorFactory;
import com.samenea.payments.model.banking.DepositChargeService;
import com.samenea.payments.order.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author: Jalal Ashrafi
 * Date: 1/29/13
 */
@ContextConfiguration(locations = { "classpath:context.xml","classpath:contexts/mock.xml" })
public class DefaultDepositOrderServiceTest extends AbstractJUnit4SpringContextTests{
    private static final Logger logger = LoggerFactory.getLogger(DefaultDepositOrderServiceTest.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private DepositChargeService mockDepositChargeService;
    private CustomerInfo customerInfo;
    private int amount;
    private String depositNumber;

    private IDGenerator idGenerator;
    @Autowired
    private IDGeneratorFactory idGeneratorFactory;

    @Autowired
    private ICustomerService mockCustomerService;

    @Before
    public void setUp() throws Exception {
        depositNumber = "depositNumber";
        amount = 100;
        customerInfo = new CustomerInfo("91211212121", "a@gmail.com");

        ICustomer depositAccountOwner = new ICustomer() {
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
        List<ICustomer> depositAccountOwners = new ArrayList<ICustomer>(1);
        depositAccountOwners.add(depositAccountOwner);
        when(mockCustomerService.findCustomersOfDeposit(depositNumber)).thenReturn(depositAccountOwners);
        doNothing().when(mockDepositChargeService).probeChargeDeposit(amount, depositNumber);
    }
    //region createOrder tests
    @Test
    public void createOrder_should_create_a_checkedOut_order_with_one_lineItem() throws ChargeException {
        final DefaultDepositOrderService defaultDepositOrderService = new DefaultDepositOrderService();
        final DepositParams depositParams = new DepositParams(depositNumber, amount);

        idGenerator = mock(IDGenerator.class);
        when(idGenerator.getNextID()).thenReturn("100");
        when(idGeneratorFactory.getIDGenerator(anyString())).thenReturn(idGenerator);
        when(orderRepository.store(any(Order.class))).thenAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                return (Order) invocation.getArguments()[0];
            }
        });

        Order order = defaultDepositOrderService.createCheckedOutDepositChargeOrder(depositParams.getDepositNumber(), depositParams.getAmount(), customerInfo);
        assertNotNull(order);
        assertEquals(Order.Status.CHECKED_OUT, order.getStatus());
        assertEquals(1, order.getLineItems().size());
        assertEquals(depositParams.getProductSpec(), order.getLineItems().get(0).getProductSpec());
    }
    @Test(expected = ChargeException.class)
    public void createOrder_should_throw_exception_if_probeDepositCharge_throws_exception() throws ChargeException {
        final DefaultDepositOrderService defaultDepositOrderService = new DefaultDepositOrderService();
        final DepositParams depositParams = new DepositParams(depositNumber, amount);
        doThrow(new ChargeException("mockCode","Mocked Charge Exception")).when(mockDepositChargeService).probeChargeDeposit(amount, depositNumber);
        defaultDepositOrderService.createCheckedOutDepositChargeOrder(depositParams.getDepositNumber(), depositParams.getAmount(), customerInfo);
    }
    //endregion

}
