package com.samenea.payments.web.controller;

import com.samenea.payments.order.Order;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.order.Receipt;
import com.samenea.payments.web.controller.deposit.ChargeController;
import com.samenea.payments.web.model.View;
import com.samenea.seapay.client.PaymentManager;
import com.samenea.seapay.client.impl.CommunicationException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Date: 3/3/13
 * Time: 2:11 PM
 *
 * @Author:payam
 */
public class PostponedOrdersControllerTest {
    @Mock
    ModelMap modelMap;
    @Mock
    RedirectAttributes redirectAttributes;
    @Mock
    private OrderService orderService;
    @Mock
    private Order order;
    @Mock
    private PaymentManager paymentManager;
    @Mock
    private Receipt receipt;
    private  final String ORDER_ID="2020";
    private  final String RECEIPTID="2020";
    @InjectMocks
    private PostponedOrdersController postponedOrdersController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(order.getId()).thenReturn(Long.valueOf(2020));
        when(order.getReceipt()).thenReturn(receipt);
    }
    @Test
    public void should_be_return_to_postponeds_page() {
        final  String resultPage= postponedOrdersController.showOrders(modelMap);
        Assert.assertEquals(View.Order.POSTPONEDS, resultPage);
    }
    @Test
    public void should_be_return_to_postponeds_page_when_resolve_transaction(){
        final  String resultPage= postponedOrdersController.showOrders(modelMap);
        Assert.assertEquals(View.Order.POSTPONEDS, resultPage);

    }
   @Test
   @Ignore
    public void should_be_return_to_exception_page_when_CommunicationException(){
       when(orderService.findByOrderId(ORDER_ID)).thenReturn(order);
       doThrow(new CommunicationException("Excption Occured....:(")).when(paymentManager).processTransaction(ORDER_ID,RECEIPTID);
       final  String resultPage= postponedOrdersController.resolvePostponedOrder(redirectAttributes,ORDER_ID);
       Assert.assertEquals(View.Excption.GENERIC, resultPage);
   }

}
