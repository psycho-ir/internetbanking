package com.samenea.payments.order.event;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.Order;
import org.slf4j.Logger;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/17/13
 * Time: 11:21 AM
 */

public class OrderDelivered extends OrderStatusChanged {
    private Logger logger = LoggerFactory.getLogger(OrderDelivered.class);


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never <code>null</code>)
     */
    public OrderDelivered(Object source, Order order) {
        super(source, order);
    }
}
