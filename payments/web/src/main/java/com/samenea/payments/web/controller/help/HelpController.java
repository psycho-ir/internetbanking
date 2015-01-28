package com.samenea.payments.web.controller.help;

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
@RequestMapping("/help")
public class HelpController {

    @RequestMapping("/deposit")
    public String deposit() {
        return	View.Help.DEPOSIT;
    }
    @RequestMapping("/loan")
    public String loan() {
        return	View.Help.LOAN;

    }
    @RequestMapping("/tracking")
    public String tracking() {
        return	View.Help.TRACKING;

    }
}