package com.samenea.payments.order;

import com.samenea.commons.component.model.BasicRepository;

import java.util.Date;
import java.util.List;

/**
 * @author: Jalal Ashrafi
 * Date: 1/28/13
 */
public interface OrderRepository extends BasicRepository<Order, Long> {
    /**
     *
     * @param orderId should not be null or empty
     * @return order with this orderId it is not null
     * @throws com.samenea.commons.component.model.exceptions.NotFoundException if there is no such order
     */
    public Order findByOrderId(String orderId);

    List<Order> findByStatus(Order.Status status);

    List<Order> findOrderContainingSimilarSpec(String productName, String criteria);

    List<Order> findOrdersContainingProduct(String productName, String spec, Date from, Date to);
}
