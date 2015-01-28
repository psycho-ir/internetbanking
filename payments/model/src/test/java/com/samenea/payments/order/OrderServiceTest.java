package com.samenea.payments.order;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.commons.idgenerator.service.IDGenerator;
import com.samenea.commons.idgenerator.service.IDGeneratorFactory;
import com.samenea.payments.order.delivery.DeliveryService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author: Jalal Ashrafi
 * Date: 1/23/13
 */
@ContextConfiguration(locations = {"classpath:context.xml", "classpath:contexts/mock.xml"})
public class OrderServiceTest extends AbstractJUnit4SpringContextTests {
    DefaultOrderService orderService;
    private IDGenerator idGenerator;
    @Autowired
    public IDGeneratorFactory idGeneratorFactory;
    @Autowired
    private DeliveryService mockDeliveryService;
    @Autowired
    private OrderRepository orderRepository;
    private CustomerInfo customerInfo;
    private String generatedID;

    @Before
    public void setUp() throws Exception {
        orderService = new DefaultOrderService();
        customerInfo = new CustomerInfo("91211212121", "a@gmail.com");
        reset(idGeneratorFactory);
        generatedID = "1";
        idGenerator = mock(IDGenerator.class);
        when(idGenerator.getNextID()).thenReturn(generatedID);
    }

    @Test
    public void createOrder_should_create_order_with_unique_orderId() {

        when(idGeneratorFactory.getIDGenerator(DefaultOrderService.ORDERS_ID_GENERATION_TOKEN)).thenReturn(idGenerator);
        when(orderRepository.store(any(Order.class))).thenAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                return (Order) invocation.getArguments()[0];
            }
        });

        Order order = orderService.createOrder(customerInfo);
        Assert.assertEquals(DefaultOrderService.ORDER_ID_PREFIX + generatedID, order.getOrderId());
    }

    @Test
    public void addLineItem_should_add_a_lineItem_to_order() {
        final String orderId = "ORD-100";
        final Order order = new Order(orderId, customerInfo);
        when(orderRepository.findByOrderId(orderId)).thenReturn(order);
        when(orderRepository.store(any(Order.class))).thenAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                return (Order) invocation.getArguments()[0];
            }
        });
        final Order returnedOrder = orderService.addLineItem(orderId, new ProductSpec("TShirt", "color = black"), 100);
        assertNotNull("order should not be null", returnedOrder);
        assertEquals(1, returnedOrder.getLineItems().size());
    }

    @Test
    public void createCheckoutOrder_should_create_Order_with_1lineItem_and_checkout_status() {
        when(idGeneratorFactory.getIDGenerator(DefaultOrderService.ORDERS_ID_GENERATION_TOKEN)).thenReturn(idGenerator);
        when(orderRepository.store(any(Order.class))).thenAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                return (Order) invocation.getArguments()[0];
            }
        });
        final Order order = orderService.createCheckedOutOrder(customerInfo, new ProductSpec("TShirt", "color = black and size=10"), 100);
        assertNotNull("created order should not be null.", order);
        assertEquals(Order.Status.CHECKED_OUT, order.getStatus());

    }

    //region getOrderAmount tests
    @Test
    public void getOrderAmount_should_return_totalAmount_of_found_Order() {
        final String orderId = "orderId";
        Order order = new Order(orderId, customerInfo);
        order.addLineItem(new ProductSpec("Deposit", "accountId = 12"), 100);

        when(orderRepository.findByOrderId(orderId)).thenReturn(order);
        final int amount = orderService.orderAmount(orderId);
        assertEquals(order.getTotalPrice(), amount);
    }

    //endregion
    //region deliver tests
    @Test
    public void deliver_should_call_charge_deposit_on_banking() throws ChargeException {
        Order order = new Order("orderId", customerInfo);
        final ProductSpec depositSpec = new ProductSpec("Deposit", "accountId = 12");
        order.addLineItem(depositSpec, 100);
        order.checkOut();
        when(orderRepository.findByOrderId(order.getOrderId())).thenReturn(order);

        final String transactionId = "transactionId";
        orderService.deliver(order.getOrderId(), transactionId, "mellat");
        verify(mockDeliveryService).deliver(eq(depositSpec), eq(order.getOrderId()), eq(transactionId), anyString());
    }
    //endregion

}
