package com.samenea.payments.web.controller;

import com.samenea.commons.component.utils.Environment;
import com.samenea.commons.tracking.service.TrackingService;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.translation.TrackingTranslator;
import com.samenea.payments.web.model.View;
import com.samenea.seapay.client.PaymentManager;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.when;

/**
 * Date: 2/3/13
 * Time: 4:01 PM
 *
 * @Author:payam
 */
@ContextConfiguration(locations = { "classpath:context.xml","classpath*:contexts/mock.xml"})
public class TransactionResultControllerTest extends AbstractJUnit4SpringContextTests {
    @Mock
    PaymentManager paymentManager;
    @Mock
    OrderService orderService;
    String transactionId="2020";
    String orderId="1010";
    String result="OTHER";

    @Mock
    TrackingService trackingService;

    @Mock
    TrackingTranslator trackingTranslator;
    @Mock
    HttpSession session;
    @Mock
    Environment environment;
    @Mock
    ModelMap modelMap;
    @InjectMocks
    private TransactionResultController transactionResultController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(trackingService.record(any(String.class), any(String.class))).thenReturn(null);
        when(trackingService.makeSynonym(any(String.class), any(String.class))).thenReturn(null);
        when(trackingTranslator.translate(any(String.class),any(String.class))).thenReturn("message");

    }
   @Test
    public void should_be_return_to_failed_page_when_result_not_equals_PAYMENT_OK(){
       final String resultPage=transactionResultController.process(session,transactionId,orderId,result,"123",modelMap);
       Assert.assertEquals(View.Deposit.FAILED, resultPage);
   }
    @Test
    public void should_be_return_to_result_page_when_result_equals_PAYMENT_OK(){
        result="PAYMENT_OK";
        final String resultPage=transactionResultController.process(session,transactionId,orderId,result,"123",modelMap);
        Assert.assertEquals(View.Deposit.RESULT, resultPage);
    }

}
