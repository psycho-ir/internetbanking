package com.samenea.payments.model;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author: Jalal Ashrafi
 * Date: 1/28/13
 */
public class DeliveryException extends RuntimeException {
    public DeliveryException() {
        super();
    }

    public DeliveryException(String message) {
        super(message);
    }

    public DeliveryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryException(Throwable cause) {
        super(cause);
    }

    protected DeliveryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
