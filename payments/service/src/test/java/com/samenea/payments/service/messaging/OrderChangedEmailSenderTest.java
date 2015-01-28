package com.samenea.payments.service.messaging;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.ProductSpec;
import com.samenea.payments.order.Receipt;
import com.samenea.payments.order.event.OrderStatusChanged;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/17/13
 * Time: 2:01 PM
 */
@Ignore
@ContextConfiguration("classpath*:context.xml")
public class OrderChangedEmailSenderTest extends AbstractJUnit4SpringContextTests {
    private Logger logger = LoggerFactory.getLogger(OrderChangedEmailSenderTest.class);
    @Mock
    OrderChangedEmailSender orderChangedEmailSender;
    @Mock
    OrderStatusChanged event;
    @Test
    @Ignore
    public void should_send_sms() throws InterruptedException {
        final CustomerInfo customerInfo = new CustomerInfo("09122502092", "soroosh.sarabadani@gmail.com");
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "paymentSystem");
        order.postponeDelivery(receipt);
        Thread.sleep(10000);

    }



}
