package com.samenea.payments.web.model.common;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: payam
 * Date: 7/25/13
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CustomTimeValidator {
    private static Logger logger = LoggerFactory.getLogger(CustomTimeValidator.class);

    public boolean isValid(String startTime, String endTime) {

        Date startDate = new Date();
        Date endtDate = new Date();

        String[] startTimeParts = startTime.split(":");
        String[] endTimeParts = endTime.split(":");
        if (startTimeParts.length < 2 || endTimeParts.length < 2) {

            throw new SamenRuntimeException("invalid time");

        }
        Date currentDate = new Date();
        startDate.setHours(Integer.parseInt(startTimeParts[0]));
        startDate.setMinutes(Integer.parseInt(startTimeParts[1]));
        endtDate.setHours(Integer.parseInt(endTimeParts[0]));
        endtDate.setMinutes(Integer.parseInt(endTimeParts[1]));
        logger.info("currentDate:" + currentDate + "_startDate:" + startDate + "endDate:" + endtDate);

        if (currentDate.after(startDate) && currentDate.before(endtDate)) {
            return true;
        }
        return false;

    }


}
