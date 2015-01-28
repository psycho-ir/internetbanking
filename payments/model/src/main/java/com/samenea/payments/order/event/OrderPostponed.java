package com.samenea.payments.order.event;

import com.samenea.payments.order.Order;
import org.slf4j.Logger;
import com.samenea.commons.component.utils.log.LoggerFactory;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/17/13
 * Time: 11:44 AM
 */

public class OrderPostponed extends OrderStatusChanged {
    private Logger logger = LoggerFactory.getLogger(OrderPostponed.class);

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public OrderPostponed(Object source, Order order) {
        super(source, order);
    }
}
