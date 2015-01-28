package com.samenea.payments.service.resolve;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.*;
import com.samenea.seapay.client.PaymentManager;
import com.samenea.seapay.client.PaymentSession;
import com.samenea.seapay.client.SeapayGatewayWebService;
import com.samenea.seapay.client.ws.*;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * @author: Soroosh Sarabadani
 * Date: 4/8/13
 * Time: 12:16 PM
 */
@Ignore
@ContextConfiguration({"classpath*:contexts/mock.xml", "classpath*:context.xml"})
public class OrderResolverServiceImplIntegrationTest extends AbstractJUnit4SpringContextTests {
    public static final String ORDER_ID = "1";
    private Logger logger = LoggerFactory.getLogger(OrderResolverServiceImplIntegrationTest.class);

    @Autowired
    private OrderResolverService orderResolverService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentManager paymentManager;
    @Autowired
    private DefaultOrderService orderService;

    @Autowired
    private PaymentSession paymentSession;
    @Autowired
    private SeapayGatewayWebService seapayGatewayWebService;

    private CustomerInfo customerInfo = new CustomerInfo("09111111111", "a@b");

    @Before
    public void before() {
        reset(orderService, paymentSession);

    }

    @Test
    public void resolve_should_make_order_CANCELED_when_it_is_canceled_in_seapay() {
        final ArrayList<Order> orders = new ArrayList<Order>();
        final Order order = new Order(ORDER_ID, customerInfo);
        final Order canceledOrder = spy(order);
        when(canceledOrder.getCreateDate()).thenReturn(new Date(new Date().getTime() - 999999999));
        canceledOrder.checkOut();
        canceledOrder.assignTransaction("TRN-100");
        orders.add(canceledOrder);
        when(orderService.orderAmount(ORDER_ID)).thenReturn(200);
        when(paymentSession.getTransactionStatus("TRN-100")).thenReturn(TransactionStatus.FAILED);
        doCallRealMethod().when(orderService).cancel(ORDER_ID);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                canceledOrder.cancel();
                return null;
            }
        }).when(orderService).cancel(ORDER_ID);

        when(orderService.findByStatus(Order.Status.CHECKED_OUT)).thenReturn(orders);
        orderResolverService.resolveAllOrders();
        Assert.assertEquals(Order.Status.CANCELED, canceledOrder.getStatus());
    }

    @Test
    public void resolve_should_not_change_order_when_its_status_in_seapay_is_UNKNOWN() throws AuthenticationException_Exception, IllegalStateException_Exception, CommunicationException_Exception, NotFoundException_Exception {
        final ArrayList<Order> orders = new ArrayList<Order>();
        final Order canceledOrder = new Order(ORDER_ID, customerInfo);
        canceledOrder.checkOut();
        canceledOrder.assignTransaction("TRN-100");
        orders.add(canceledOrder);
        when(orderService.orderAmount(ORDER_ID)).thenReturn(200);
        when(paymentSession.getTransactionStatus("TRN-100")).thenReturn(TransactionStatus.UNKNOWN);
        when(orderService.findByStatus(Order.Status.CHECKED_OUT)).thenReturn(orders);
        orderResolverService.resolveAllOrders();
        verify(orderService, times(0)).deliver(anyString(), anyString(),anyString());
        verify(paymentSession, times(0)).commitTransaction(anyString(), anyInt());
        Assert.assertEquals(Order.Status.CHECKED_OUT, canceledOrder.getStatus());
    }

    @Test
    public void resolve_should_process_just_orders_with_age_more_than_120min() {
        final ArrayList<Order> orders = new ArrayList<Order>();
        final Order youngOrder = new Order("2", customerInfo);
        youngOrder.checkOut();
        youngOrder.assignTransaction("TRN-100");
        final Order oldOrder = mock(Order.class);
        when(oldOrder.getCreateDate()).thenReturn(new Date(new Date().getTime() - 999999999));
        when(oldOrder.getAssignedTransactionId()).thenReturn("TRN-101");
        when(oldOrder.getOrderId()).thenReturn("3");
        orders.add(youngOrder);
        orders.add(oldOrder);
        when(orderService.orderAmount(ORDER_ID)).thenReturn(200);
        when(paymentSession.getTransactionStatus("TRN-100")).thenReturn(TransactionStatus.UNKNOWN);
        when(orderService.findByStatus(Order.Status.CHECKED_OUT)).thenReturn(orders);
        orderResolverService.resolveAllOrders();

        verify(paymentManager, times(0)).processTransaction("2", "TRN-100");
        verify(paymentManager).processTransaction("3", "TRN-101");
    }

    @Test
    public void resolve_should_deliver_order_when_its_status_in_seapay_is_COMMITED() {
        final ArrayList<Order> orders = new ArrayList<Order>();
        Order order = new Order(ORDER_ID, customerInfo);
        final Order canceledOrder = spy(order);
        when(canceledOrder.getCreateDate()).thenReturn(new Date(new Date().getTime() - 999999999));
        canceledOrder.checkOut();
        canceledOrder.assignTransaction("TRN-100");
        orders.add(canceledOrder);
        when(orderService.orderAmount(ORDER_ID)).thenReturn(200);
        when(paymentSession.getTransactionStatus("TRN-100")).thenReturn(TransactionStatus.COMMITED);
        when(orderService.findByStatus(Order.Status.CHECKED_OUT)).thenReturn(orders);
        doAnswer(new Answer() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                canceledOrder.deliver(new Receipt("1", "P"));
                return null;
            }
        }).when(orderService).deliver(eq(ORDER_ID), eq("TRN-100"), anyString());

        orderResolverService.resolveAllOrders();
        verify(orderService, times(1)).deliver(anyString(), anyString(),anyString());
        Assert.assertEquals(Order.Status.DELIVERED, canceledOrder.getStatus());
    }

}
