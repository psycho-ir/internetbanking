package com.samenea.payments.model;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.Order;

/**
 *
 * @author: Jalal Ashrafi
 * Date: 1/29/13
 */
public interface DepositOrderService {
    /**
     *
     * @param depositNumber
     * @param amount
     * @param customerInfo
     * @return
     * @throws ChargeException
     * @throws com.samenea.payments.model.LimitExceededException if violates the charge limit per day
     * It is deprecated. In near future! it should be replaced by a method for creating  product spec in this service and
     * using {@link com.samenea.payments.order.OrderService#createCheckedOutOrder(com.samenea.payments.order.CustomerInfo, com.samenea.payments.order.ProductSpec, int)}
     *
     */
    @Deprecated
    Order createCheckedOutDepositChargeOrder(String depositNumber, int amount, CustomerInfo customerInfo) throws ChargeException;

}
