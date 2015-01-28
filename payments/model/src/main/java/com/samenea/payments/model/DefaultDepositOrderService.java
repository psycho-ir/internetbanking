package com.samenea.payments.model;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.customer.ICustomerService;
import com.samenea.banking.deposit.ChargeException;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.banking.DepositChargeService;
import com.samenea.payments.order.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.samenea.payments.order.Order.Status.*;

/**
 * @author: Jalal Ashrafi
 * Date: 1/29/13
 */
@Configurable
@Component
public class DefaultDepositOrderService implements DepositOrderService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultDepositOrderService.class);
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    DepositChargeService depositChargeService;
    @Autowired
    ICustomerService customerService;
    @Autowired
    @Value("${charge.depositLimitPerDay}")
    private Integer depositLimitPerDay;

    @Override
    @Transactional
    public Order createCheckedOutDepositChargeOrder(String depositNumber, int amount, CustomerInfo customerInfo) throws ChargeException {
        depositChargeService.probeChargeDeposit(amount, depositNumber);
        Integer todayTotalDeposits = todayTotalDepositForCustomer(depositNumber);
        if (todayTotalDeposits + amount > depositLimitPerDay) {
            throw new LimitExceededException("Could not charge more than limit " + depositLimitPerDay.toString(), depositLimitPerDay);
        }
        final Order order = orderService.createOrder(customerInfo);
        final DepositParams depositParams = new DepositParams(depositNumber, amount);
        final ProductSpec productSpec = depositParams.getProductSpec(getUserInfo(depositNumber));
        order.addLineItem(productSpec, amount);
        order.checkOut();
        return orderRepository.store(order);
    }

    private Integer todayTotalDepositForCustomer(String depositNumber) {
        final Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        instance.set(Calendar.MILLISECOND,0);
        Date from = instance.getTime();
        instance.add(Calendar.DAY_OF_MONTH,1);
        Date to = instance.getTime();
        final List<Order> ordersContainingProduct = orderRepository.findOrdersContainingProduct(DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME, DepositParams.createEqualCriteria(DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME, depositNumber), from, to);
        Integer todayTotalDeposits = 0;
        for (Order order : ordersContainingProduct) {
            if (order.getStatus() == DELIVERED || order.getStatus() == CHECKED_OUT || order.getStatus() == POSTPONED) {
                for (LineItem lineItem : order.getLineItems()) {
                    if (lineItem.getProductSpec().getProductName().equals(DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME)) {
                        todayTotalDeposits += lineItem.getPrice();
                    }
                }

            }
        }
        return todayTotalDeposits;
    }

    private UserInfo getUserInfo(String depositNumber) {
        final List<ICustomer> customersOfDeposit = customerService.findCustomersOfDeposit(depositNumber);
        String firstNames = "";
        String lastNames = "";
        for (int i = 0; i < customersOfDeposit.size(); i++) {
            ICustomer iCustomer = customersOfDeposit.get(i);
            if(i >= 1){
              firstNames +="-";
              lastNames +="-";
            }
            firstNames += iCustomer.getName();
            lastNames += iCustomer.getLastName();
        }
        return new UserInfo(firstNames, lastNames);
    }
}
