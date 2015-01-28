package com.samenea.payments.web.controller.contactus;

import com.samenea.payments.web.model.View;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Date: 1/22/13
 * Time: 3:29 PM
 *
 * @Author:payam
 */

@Controller
@RequestMapping("/contactus")
public class ContactUSController {

    @RequestMapping("/technical")
    public String technical() {
        return	View.ContactUS.TECHNICAL;
    }
}