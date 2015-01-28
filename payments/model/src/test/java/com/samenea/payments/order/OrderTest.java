package com.samenea.payments.order;

import com.samenea.commons.component.utils.Environment;
import com.samenea.payments.order.delivery.DeliveryService;
import com.samenea.payments.testutils.ApplicationEventCollector;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.Date;

import static com.samenea.payments.order.Order.Status.DELIVERED;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author: Jalal Ashrafi
 * Date: 1/23/13
 */
@ContextConfiguration(locations = {"classpath:context.xml", "classpath:contexts/mock.xml"})
public class OrderTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private Environment environment;
    @Autowired
    private ApplicationEventCollector applicationEventCollector;
    private CustomerInfo customerInfo;

    @Before
    public void setUp() throws Exception {
        customerInfo = new CustomerInfo("91211212121", "a@gmail.com");
        when(environment.getCurrentDate()).thenReturn(new Date());
        reset(deliveryService);
        applicationEventCollector.reset();
    }

    //region constructor param validations
    @Test(expected = IllegalArgumentException.class)
    public void constructor_does_not_accept_null_orderId() {
        new Order(null, customerInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_does_not_accept_empty_orderId() {
        new Order(" ", customerInfo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_does_not_accept_null_customerInfo() {
        new Order("orderId", null);
    }
    //endregion

    //region checkout tests
    @Test
    public void addLineItem_should_add_a_line_item_in_order() {
        final String phoneNumber = "phoneNumber";
        final String email = "email";
        final Order order = new Order("ORD-1", new CustomerInfo(phoneNumber, email));
        final int item1Price = 100;
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), item1Price);
        final int item2Price = 200;
        order.addLineItem(new ProductSpec("Product1", "prop1 == value1 and prop2 == value2"), item2Price);
        assertEquals(2, order.getLineItems().size());
        assertEquals(item1Price + item2Price, order.getTotalPrice());
    }
    //endregion

    //region checkout tests
    @Test(expected = IllegalStateException.class)
    public void checkOut_should_disable_adding_line_items() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();

        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
    }

    @Test
    public void checkOut_could_be_called_multiple_times() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.checkOut();
    }

    @Test(expected = IllegalStateException.class)
    public void checkOut_should_not_be_possible_for_already_delivered_order() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.deliver(new Receipt("rid", "mellat"));

        order.checkOut();
    }
    //endregion

    //region deliver tests
    @Test()
    public void deliver_should_call_delivery_service_on_every_line_items_product_spec() {
        final Order order = new Order("ORD-1", customerInfo);
        final ProductSpec productSpec1 = new ProductSpec("Deposit", "accountId == 12221");
        final ProductSpec productSpec2 = new ProductSpec("Product", "prop1 == value1 and prop2 == value2");

        order.addLineItem(productSpec1, 100);
        order.addLineItem(productSpec2, 100);
        order.checkOut();

        final Receipt receipt = new Receipt("receiptId", "mellat");
        order.deliver(receipt);
        assertEquals(receipt, order.getReceipt());
        assertEquals(DELIVERED, order.getStatus());

        verify(deliveryService).deliver(eq(productSpec1), eq(order.getOrderId()), eq(receipt.getReceiptId()), anyString());
        verify(deliveryService).deliver(eq(productSpec2), eq(order.getOrderId()), eq(receipt.getReceiptId()), anyString());

    }

    @Test()
    public void deliver_should_call_delivery_service_on_every_line_items_product_spec_for_Postponed_Delivery_Order() {
        final Order order = new Order("ORD-1", customerInfo);
        final ProductSpec productSpec1 = new ProductSpec("Deposit", "accountId == 12221");
        final ProductSpec productSpec2 = new ProductSpec("Product", "prop1 == value1 and prop2 == value2");

        order.addLineItem(productSpec1, 100);
        order.addLineItem(productSpec2, 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "mellat");
        order.postponeDelivery(receipt);

        order.deliver(receipt);
        assertEquals(receipt, order.getReceipt());
        assertEquals(DELIVERED, order.getStatus());

        verify(deliveryService).deliver(eq(productSpec1), eq(order.getOrderId()), eq(receipt.getReceiptId()), anyString());
        verify(deliveryService).deliver(eq(productSpec2), eq(order.getOrderId()), eq(receipt.getReceiptId()), anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void deliver_should_not_accept_different_receipt_for_Postponed_Delivery_Order() {
        final Order order = new Order("ORD-1", customerInfo);
        final ProductSpec productSpec1 = new ProductSpec("Deposit", "accountId == 12221");
        final ProductSpec productSpec2 = new ProductSpec("Product", "prop1 == value1 and prop2 == value2");

        order.addLineItem(productSpec1, 100);
        order.addLineItem(productSpec2, 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "paymentSystem");
        order.postponeDelivery(receipt);

        final Receipt inconsistentReceipt = new Receipt("diff", "paymentSystem");
        order.deliver(inconsistentReceipt);

    }

    @Test(expected = IllegalStateException.class)
    public void deliver_should_not_be_possible_before_checkout() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.deliver(new Receipt("receiptId", "paymentSystem"));
    }

    @Test(expected = IllegalStateException.class)
    public void deliver_should_not_be_possible_for_already_delivered_order() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.deliver(new Receipt("receiptId", "mellat"));
        order.deliver(new Receipt("receiptId", "mellat"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deliver_should_not_allow_null_receipt() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.deliver(null);
    }

    @Test
    public void deliver_should_raise_event_OnOrderDelivered() {
        final Order order = new Order("ORD-1", customerInfo);
        final ProductSpec productSpec1 = new ProductSpec("Deposit", "accountId == 12221");
        final ProductSpec productSpec2 = new ProductSpec("Product", "prop1 == value1 and prop2 == value2");
        order.addLineItem(productSpec1, 100);
        order.addLineItem(productSpec2, 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "mellat");
        order.deliver(receipt);

        Assert.assertEquals(1, applicationEventCollector.countCollectedEvents());
        Assert.assertTrue(this.applicationEventCollector.getCollectedEvents().get(0).getSource().equals(order));
    }
    //endregion

    //region postponeDelivery tests
    @Test
    public void postponeDelivery_should_set_receipt_when_order_is_in_checkedout_state() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "paymentSystem");
        order.postponeDelivery(receipt);
        assertEquals(Order.Status.POSTPONED, order.getStatus());
        assertEquals(receipt, order.getReceipt());
    }

    @Test
    public void postponeDelivery_could_be_called_multiple_times() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "paymentSystem");
        order.postponeDelivery(receipt);
        final Receipt receipt2 = new Receipt("receiptId2", "paymentSystem");
        order.postponeDelivery(receipt2);
        assertEquals(Order.Status.POSTPONED, order.getStatus());
        assertEquals(receipt2, order.getReceipt());
    }

    @Test(expected = IllegalStateException.class)
    public void postponeDelivery_should_not_be_possible_in_MODIFIABLE_state() {
        final Order order = new Order("ORD-1", customerInfo);
        order.postponeDelivery(new Receipt("receiptId", "paymentSystem"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void postponeDelivery_should_not_accept_null_receipt() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.postponeDelivery(null);
    }

    @Test
    public void postponeDelivery_should_raise_event_OnOrderPosponed() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        final Receipt receipt = new Receipt("receiptId", "paymentSystem");
        order.postponeDelivery(receipt);
        Assert.assertEquals(1, applicationEventCollector.countCollectedEvents());
        Assert.assertTrue(applicationEventCollector.getCollectedEvents().get(0).getSource().equals(order));
    }

    //endregion

    //region Cancel Tests
    @Test()
    public void cancel_should_set_order_status_to_canceled() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.cancel();
        assertEquals(Order.Status.CANCELED, order.getStatus());
    }

    @Test(expected = IllegalStateException.class)
    public void cancel_should_not_be_possible_in_DELIVERED_status() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.deliver(new Receipt("recieptId", "mellat"));
        order.cancel();
    }
    //endregion

    @Test
    public void assignTransction_should_set_assignedTransaction_forCheckedOutOrder() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        final String assignedTransactionId = "assignedTransactionId";
        order.assignTransaction(assignedTransactionId);
        assertEquals(assignedTransactionId, order.getAssignedTransactionId());
    }

    @Test
    public void assignTransction_should_allow_reassign() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        final String assignedTransactionId = "assignedTransactionId";
        order.assignTransaction(assignedTransactionId);
        final String reassignedTransactionId = "reassignedTransactionId";
        order.assignTransaction(reassignedTransactionId);
        assertEquals(reassignedTransactionId, order.getAssignedTransactionId());
    }

    @Test(expected = IllegalStateException.class)
    public void assignTransction_should_not_be_possible_MODIFIABLE_State() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.assignTransaction("assignedTransactionId");
    }

    @Test(expected = IllegalStateException.class)
    public void assignTransction_should_not_be_possible_DELIVERED_State() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.checkOut();
        order.deliver(new Receipt("recieptId", "mellat"));
        order.assignTransaction("assignedTransactionId");
    }

    @Test(expected = IllegalStateException.class)
    public void assignTransction_should_not_be_possible_CANCELED_State() {
        final Order order = new Order("ORD-1", customerInfo);
        order.addLineItem(new ProductSpec("Product", "prop1 == value1 and prop2 == value2"), 100);
        order.cancel();
        order.assignTransaction("assignedTransactionId");
    }

}
