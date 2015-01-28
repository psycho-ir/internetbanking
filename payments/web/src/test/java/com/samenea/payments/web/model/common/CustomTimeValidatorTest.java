package com.samenea.payments.web.model.common;

import com.samenea.commons.component.utils.exceptions.SamenRuntimeException;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.loan.LoanInstalmentViewModel;
import com.samenea.seapay.client.impl.CommunicationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javax.validation.Validation;

import static org.mockito.Mockito.doThrow;

/**
 * Created with IntelliJ IDEA.
 * User: payam
 * Date: 7/27/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomTimeValidatorTest {

    private CustomTimeValidator customTimeValidator;


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        customTimeValidator=new CustomTimeValidator();
    }

    @Test(expected= SamenRuntimeException.class)
    public void SamenRuntimeException_throw_when_inout_time_not_valid() throws SamenRuntimeException {
         String startTime="10";
         String endTime="0;";
        customTimeValidator.isValid(startTime,endTime);

    }
    @Test
    public void   return_true_when_currentTime_between_startTime_and_endTime(){
         String startTime_valid="00:59";
         String endTime_valid="23:59";
        boolean result =customTimeValidator.isValid(startTime_valid,endTime_valid);
        Assert.assertEquals(result,true);
    }

    @Test
    public void return_false_when_currentTime_befor_startTime_and_after_endTime()
    {
        String startTime="23:59";
        String endTime="00:00";
        boolean result =customTimeValidator.isValid(startTime,endTime);
        Assert.assertEquals(result,false);
    }

    @Test
    public void return_false_when_currentTime_before_startTime_and_after_endTime0()
    {
        String startTime="00:02";
        String endTime="22:30";
        boolean result =customTimeValidator.isValid(startTime,endTime);
        Assert.assertEquals(result,true);
    }
}
