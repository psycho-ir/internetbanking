package com.samenea.payments.order.delivery;

import com.samenea.payments.order.ProductSpec;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
public interface DeliveryService {
    /**
     * @param productSpec
     * @param orderId
     * @param transactionId @throws DeliveryService if can not deliver such product
     */
    Delivery deliver(ProductSpec productSpec, String orderId, String transactionId, String debitNumber);
}
