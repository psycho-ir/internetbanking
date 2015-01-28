package com.samenea.payments.order;


import java.util.List;

/**
 * @author: Jalal Ashrafi
 * Date: 1/23/13
 */
public interface OrderService {
    public Order findByOrderId(String orderId);
    public Order createOrder(CustomerInfo customerInfo);
    public List<Order> findByStatus(Order.Status status);

    /**
     *
     * @param orderId
     * @param productSpec
     * @param price
     * @return order with added line item
     */
    Order addLineItem(String orderId, ProductSpec productSpec, int price);
    Order checkout(String orderId);

    /**
     * A shortcut for creating single item checked out order in one transaction
     * @param customerInfo
     * @param productSpec
     * @param amount
     * @return created checked out order
     */
    Order createCheckedOutOrder(CustomerInfo customerInfo, ProductSpec productSpec, int amount);

    void cancel(String orderId);
}
