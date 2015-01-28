package com.samenea.payments.order;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.idgenerator.service.IDGeneratorFactory;
import com.samenea.seapay.client.DoubleSpendingException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author: Jalal Ashrafi
 * Date: 1/23/13
 */

@Component
@Configurable
public class DefaultOrderService implements OrderService, com.samenea.seapay.client.OrderService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultOrderService.class);

    public static final String ORDER_ID_PREFIX = "ORD-";
    public static final String ORDERS_ID_GENERATION_TOKEN = "PaymentOrderIds";
    public static final String SEAPAY = "Seapay";

    @Autowired
    IDGeneratorFactory idGeneratorFactory;

    @Autowired
    OrderRepository orderRepository;

    @Transactional(readOnly = true)
    @Override
    public Order findByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Order createOrder(CustomerInfo customerInfo) {
        final Order order = new Order(generateOrderId(), customerInfo);
        return orderRepository.store(order);
    }

    private String generateOrderId() {
        return ORDER_ID_PREFIX + idGeneratorFactory.getIDGenerator(ORDERS_ID_GENERATION_TOKEN).getNextID();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> findByStatus(Order.Status status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Order addLineItem(String orderId, ProductSpec productSpec, int price) {
        final Order order = orderRepository.findByOrderId(orderId);
        order.addLineItem(productSpec, price);
        return orderRepository.store(order);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Order checkout(String orderId) {
        final Order order = orderRepository.findByOrderId(orderId);
        order.checkOut();
        return orderRepository.store(order);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Order createCheckedOutOrder(CustomerInfo customerInfo, ProductSpec productSpec, int amount) {
        final Order order = new Order(generateOrderId(), customerInfo);
        order.addLineItem(productSpec, amount);
        order.checkOut();
        return orderRepository.store(order);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deliver(String orderId, String transactionId, String bankName) {
        final Order order = orderRepository.findByOrderId(orderId);
        order.deliver(new Receipt(transactionId, bankName));
        logger.debug("Order delivered: {}", order.toString());
    }


    @Transactional(readOnly = true)
    @Override
    public int orderAmount(String orderId) {
        return orderRepository.findByOrderId(orderId).getTotalPrice();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void postponeDelivery(String orderId, String transactionId) {
        final Order order = orderRepository.findByOrderId(orderId);
        order.postponeDelivery(new Receipt(transactionId, SEAPAY));
        orderRepository.store(order);
    }

    /**
     * This is a callback method in seapay client which requires transaction be new
     * because enclosing method requires this
     *
     * @param orderId
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void cancel(String orderId) {
        Assert.hasText(orderId, "orderId should not be null or empty.");
        final Order order = orderRepository.findByOrderId(orderId);
        order.cancel();
        orderRepository.store(order);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void assignTransaction(String orderId, String transactionId) {
        final Order order = orderRepository.findByOrderId(orderId);
        order.assignTransaction(transactionId);
        orderRepository.store(order);
    }

    @Override
    public void checkDoubleSpending(String orderId, String transactionId) throws DoubleSpendingException {
        final Order order = orderRepository.findByOrderId(orderId);
        //another implementation is checking if there is any other order with this transactionid as receiptid, but it also do the job
        if (!transactionId.equals(order.getAssignedTransactionId())) {
            throw new DoubleSpendingException("Assigned transactionId is :" + orderId + "But for paying this order is sent: " + transactionId);
        }
    }
}
