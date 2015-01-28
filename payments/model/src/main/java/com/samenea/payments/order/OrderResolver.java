package com.samenea.payments.order;

/**
 * @author: Jalal Ashrafi
 * Date: 6/15/13
 */
public interface OrderResolver {
    /**
     * Tries to resolve order
     * @param order
     * @return true if order is resolved false if it can not be resolve now
     */
    void resolve(Order order);
}
