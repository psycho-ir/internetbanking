package com.samenea.payments.order;


import com.samenea.commons.component.model.Entity;
import com.samenea.commons.component.utils.Environment;
import com.samenea.payments.order.delivery.DeliveryService;
import com.samenea.payments.order.event.OrderDelivered;
import com.samenea.payments.order.event.OrderPostponed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Jalal Ashrafi
 * Date: 1/23/13
 */

@javax.persistence.Entity
@Table(name = "XORDER")
@Configurable(preConstruction = true)
public class Order extends Entity<Long> implements ApplicationEventPublisherAware {

    public static enum Status {
        /**
         * When order is created it's state is {@link #MODIFIABLE} until it is checked out
         */
        MODIFIABLE,
        /**
         * order is checkout for payment, thus order content can not be changed any more
         */
        CHECKED_OUT,
        /**
         * When for some reason such as unknown status for payments order can not be delivered or checkedout now
         */
        POSTPONED,
        /**
         * Order Items are delivered
         */
        DELIVERED,
        /**
         * Order is canceled
         */
        CANCELED,
        /**
         * When order is reversed in core
         */
        REVERESED


    }

    @Transient
    private ApplicationEventPublisher eventPublisher;
    @Column(length = 50, unique = true, nullable = false, updatable = false)
    private final String orderId;
    @Column(length = 50, unique = true)
    private String assignedTransactionId;

    private final Date createDate;

    private Date lastUpdateDate;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "OrderLineItems")
    private List<LineItem> lineItems;

    @Enumerated(value = EnumType.STRING)
    private Status status;

   /* @Value("${banking.mellat.debitNumber}")
    private String mellatDebitNumber;
    @Value("${banking.saderat.debitNumber]")
    private String saderatDebitNumber;
    @Value("${banking.stub.debitNumber}")
    private String stubDebitNumber;*/

    @Qualifier(value = "deliveryDispatcher")
    @Transient
    @Autowired
    DeliveryService deliveryService;
    @Transient
    @Autowired(required = true)
    private Environment environment;
    @Embedded
    private Receipt receipt;
    @Embedded()
    private final CustomerInfo customerInfo;
    @Transient
    @Autowired
    ConfigurableBeanFactory beanFactory;

    private Order() {
        this.createDate = null;
        this.orderId = null;
        customerInfo = null;
    }

    public Order(String orderId, CustomerInfo customerInfo) {
        Assert.hasText(orderId, "orderId can not be null or empty");
        Assert.notNull(customerInfo, "customerInfo can not be null or empty");
        this.orderId = orderId;
        this.lineItems = new ArrayList<LineItem>();
        this.customerInfo = customerInfo;
        status = Status.MODIFIABLE;
        this.createDate = environment.getCurrentDate();
        this.lastUpdateDate = this.createDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public int getTotalPrice() {
        int sum = 0;
        for (LineItem lineItem : lineItems) {
            sum += lineItem.getPrice();
        }
        return sum;
    }


    public void addLineItem(ProductSpec spec, int price) {
        if (this.status == Status.CHECKED_OUT) {
            throw new IllegalStateException("Order is checked out so it's content can not be changed");
        }
        lineItems.add(new LineItem(spec, price));
    }

    public void checkOut() {
        if (status == Status.DELIVERED || status == Status.REVERESED) {
            throw new IllegalStateException(String.format("Order is already " + status));
        }
        this.status = Status.CHECKED_OUT;
        this.lastUpdateDate = environment.getCurrentDate();
    }

    /**
     * @param receipt payment info
     * @throws IllegalStateException if order is not in checked out or it is delivered already
     */
    public void deliver(Receipt receipt) {
        Assert.notNull(receipt, "Receipt could not be null.");
        if (this.status != Status.CHECKED_OUT && this.status != Status.POSTPONED) {
            throw new IllegalStateException(String.format("Order status should be CHECKED OUT for delivery to be called. but is : %s", status));
        }
        if (status == Status.POSTPONED && !receipt.equals(this.receipt)) {
            throw new IllegalStateException(String.format("Passed in receipt is: {} but should be: {} . those should be" +
                    " same in postponed delivery", receipt, this.receipt));
        }

        final String debitNumber = beanFactory.resolveEmbeddedValue("${banking." + receipt.getPaymentSystem() + ".debitNumber}");

        for (LineItem lineItem : lineItems)

        {
            deliveryService.deliver(lineItem.getProductSpec(), orderId, receipt.getReceiptId(), debitNumber);
        }

        this.receipt = receipt;
        this.status = Status.DELIVERED;
        this.lastUpdateDate = environment.getCurrentDate();
        this.eventPublisher.publishEvent(new OrderDelivered(this, this));

    }

    /**
     * This will be used when currently payment transaction status is not known and marks order for later resolve
     *
     * @param receipt current receipt
     */
    public void postponeDelivery(Receipt receipt) {
        Assert.notNull(receipt, "Receipt can not be null.");
        if (status != Status.CHECKED_OUT && status != Status.POSTPONED) {
            throw new IllegalStateException(String.format("Can not postpone delivery in this state: %s", status));
        }
        this.status = Status.POSTPONED;
        this.receipt = receipt;
        this.lastUpdateDate = environment.getCurrentDate();
        this.eventPublisher.publishEvent(new OrderPostponed(this, this));
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public Status getStatus() {
        return status;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!orderId.equals(order.orderId)) return false;

        return true;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public int hashCode() {
        return orderId.hashCode();
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", deliveryService=" + deliveryService +
                ", receipt=" + receipt +
                ", customer=" + customerInfo.toString() +
                ", lineItems =\n" + getLineItemsString() +
                '}';
    }

    private String getLineItemsString() {
        String lineItemsString = "";
        for (int i = 0; i < lineItems.size(); i++) {
            LineItem lineItem = lineItems.get(i);
            lineItemsString += i + 1 + ". " + lineItem.toString() + "\n";
        }
        return lineItemsString == "" ? "No Item added" : lineItemsString;
    }

    public void cancel() {
        if (status == Status.DELIVERED || status == Status.CANCELED)
            throw new IllegalStateException(String.format("Can not cancel order in this status: %s", status));
        this.status = Status.CANCELED;
        this.lastUpdateDate = environment.getCurrentDate();
    }

    public void assignTransaction(String transactionId) {
        if (status != Status.CHECKED_OUT) {
            throw new IllegalStateException(String.format("Assigning transactionId just is possible for checked out order but current status is: %s", status));
        }
        this.assignedTransactionId = transactionId;
    }

    public String getAssignedTransactionId() {
        return assignedTransactionId;
    }
}
