package com.samenea.payments.web.controller.contactus;

import com.samenea.payments.web.controller.help.HelpController;
import com.samenea.payments.web.model.View;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Date: 3/27/13
 * Time: 10:17 AM
 *
 * @Author:payam
 */
public class ContactUSControllerTest {
    @InjectMocks
    private ContactUSController contactUSController;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void should_be_return_to_contactus_technical_page(){
        final  String resultPage=contactUSController.technical();
        Assert.assertEquals(View.ContactUS.TECHNICAL, resultPage);
    }
}
