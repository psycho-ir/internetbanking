package com.samenea.payments.web.controller;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.OrderRepository;
import com.samenea.seapay.client.PaymentManager;
import com.samenea.seapay.client.PaymentSession;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author: Jalal Ashrafi
 * Date: 2/3/13
 */
@ContextConfiguration(locations = { "classpath:context.xml"})
public class StartTransactionControllerTest extends AbstractJUnit4SpringContextTests{
    @Autowired
    PaymentManager paymentManager;
    @Autowired
    OrderRepository orderRepository;

    @Mock
    PaymentSession paymentSession;

    private static final Logger logger = LoggerFactory.getLogger(StartTransactionControllerTest.class);

    @Test
    @Ignore
    public void startTransaction_Successfull(){
//        final List<Order> all = orderRepository.getAll();
        paymentManager.startTransaction("ORD-100","No Cust");
    }
}
