package com.samenea.payments.order.event;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.Order;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/17/13
 * Time: 11:29 AM
 */

public abstract class OrderStatusChanged extends ApplicationEvent {
    private Logger logger = LoggerFactory.getLogger(OrderStatusChanged.class);

    private final Order order;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public OrderStatusChanged(Object source, Order order) {
        super(source);
        this.order = order;
        logger.info("Order:{} status is changed to {}", order.getOrderId(), order.getStatus());
    }

    public Order getOrder() {
        return order;
    }
}
