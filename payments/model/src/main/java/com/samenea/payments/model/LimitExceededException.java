package com.samenea.payments.model;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author: Jalal Ashrafi
 * Date: 1/6/14
 */
public class LimitExceededException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(LimitExceededException.class);
    private final int limit;

    public LimitExceededException(String message, int limit) {
        super(message);
        this.limit = limit;
    }

    public LimitExceededException(String message, Throwable cause, int limit) {
        super(message, cause);
        this.limit = limit;
    }

    public LimitExceededException(Throwable cause, int limit) {
        super(cause);
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }
}
