package com.samenea.payments.web.controller;

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
public class HomeController {

    @RequestMapping("/")
    public String homeHandler() {
        return	("redirect:"+View.Default.HOME_PAGE);
    }
    @RequestMapping(value="/pagenotfound" , method= RequestMethod.GET)
    public String pageNotFound(){

        return View.Error.PageNotFound;
    }
    @RequestMapping(value="/error" , method= RequestMethod.GET)
    public String error(){

        return View.Excption.GENERIC;
    }
    @RequestMapping("/index")
    public String welcomeHandler() {
        return	View.Default.HOME_PAGE;
    }
}