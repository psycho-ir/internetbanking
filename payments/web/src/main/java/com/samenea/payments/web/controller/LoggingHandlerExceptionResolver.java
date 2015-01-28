package com.samenea.payments.web.controller;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.web.model.View;
import org.slf4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Jalal Ashrafi
 * Date: 2/11/13
 */
public class LoggingHandlerExceptionResolver extends SimpleMappingExceptionResolver
        implements HandlerExceptionResolver, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(LoggingHandlerExceptionResolver.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(LoggingHandlerExceptionResolver.class, LoggerFactory.LoggerType.EXCEPTION);

    public ModelAndView resolveException(HttpServletRequest aReq, HttpServletResponse aRes, Object aHandler, Exception anExc) {
        logger.warn("Unhandled Exception Occurred. message is: {}", anExc.getMessage());
        exceptionLogger.warn("Unhandled Exception Occurred.{}",anExc.getMessage(), anExc);
        return null;
    }
}