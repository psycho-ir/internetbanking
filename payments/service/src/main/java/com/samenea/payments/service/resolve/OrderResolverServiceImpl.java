package com.samenea.payments.service.resolve;

import com.samenea.commons.component.utils.Environment;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.OrderResolver;
import com.samenea.payments.order.OrderService;
import com.samenea.seapay.client.PaymentManager;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: Soroosh Sarabadani
 * Date: 4/8/13
 * Time: 12:06 PM
 */
@Lazy
@Service
public class OrderResolverServiceImpl implements OrderResolver, OrderResolverService, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(OrderResolverServiceImpl.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(OrderResolverServiceImpl.class, LoggerFactory.LoggerType.EXCEPTION);

    @Value("${resolve.delayTime}")
    private String delayTime;
    @Value("${resolve.orderAgeMinutes}")
    private Integer orderAge;
    @Autowired
    private OrderService orderService;
    private PaymentManager paymentManager;
    @Autowired
    private Environment environment;
    private boolean contextLoaded = false;
    private ApplicationContext applicationContext;

    @Override
    public void resolveAllOrders() {
        logger.info("Order Resolver Scheduler started.");
        if (contextNotLoaded()) return;
        final List<Order> unresolvedOrders = orderService.findByStatus(Order.Status.POSTPONED);
        unresolvedOrders.addAll(orderService.findByStatus(Order.Status.CHECKED_OUT));
        if (unresolvedOrders.isEmpty()) {
            logger.info("There is no unresolved order.");
        }
        for (Order order : unresolvedOrders) {
            try {
                process(order);
            } catch (Exception e) {
                logger.warn("Error in resolving Order:{}", e.getMessage());
                exceptionLogger.error("Error in resolving Order:{} ", order.getOrderId(), e);
            }
        }
        logger.info("Order Resolver Scheduler finished. Next will start after:{} seconds.", delayTime);
    }

    private boolean contextNotLoaded() {
        if (this.contextLoaded) {
            if (this.paymentManager == null) {
                try {
                    this.paymentManager = (PaymentManager) applicationContext.getBean("defaultPaymentManager");
                } catch (Exception e) {
                    logger.info("SEAPAY Web Service is down.");
                    exceptionLogger.info("SEAPAY Web Service is down.", e);
                    return true;
                }
            }
        } else {
            logger.info("Context is not loaded for scheduling.");
            return true;
        }
        return false;
    }

    public void process(Order order) {

        logger.info("Order:{} is processing with Order Resolver Scheduler.", order.getOrderId());
        if(orderAgeIsPassed(order)){
            processOldOrder(order);
        }else{
            logger.info("Order:{} is too young for resolving. Create Date:{} - Order Age requires for resolving:{} minutes.", order.getOrderId(), order.getCreateDate(), orderAge);
        }

    }

    private void processOldOrder(Order order) {
        if (order.getStatus() == Order.Status.CHECKED_OUT && transactionIsNotAssigned(order)) {
            orderService.cancel(order.getOrderId());
            logger.info("Order:{} is canceled, Because was not attached to any transaction of SEAPAY.", order.getOrderId());
        }else{
            paymentManager.processTransaction(order.getOrderId(), order.getAssignedTransactionId());
            logger.info("Order:{} was processed with Order Resolver Scheduler. New Status:{}.", order.getOrderId(), order.getStatus());
        }
    }

    private boolean orderAgeIsPassed(Order order) {
        return order.getCreateDate().before(minutesBefore(orderAge));
    }

    private Date minutesBefore(Integer minutes) {
        return new Date(environment.getCurrentDate().getTime() - (minutes * 60 * 1000));
    }

    private boolean transactionIsNotAssigned(Order order) {
        return order.getAssignedTransactionId() == null || order.getAssignedTransactionId().equals("");
    }

    public void resolve(Order order) {
        if (contextNotLoaded()) throw new IllegalStateException("context should be loaded first.");
        paymentManager.processTransaction(order.getOrderId(), order.getAssignedTransactionId());
        logger.info("Order:{} was processed with Order Resolver Scheduler. New Status:{}.", order.getOrderId(), order.getStatus());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.contextLoaded = true;
    }
}
