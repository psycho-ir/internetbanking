package com.samenea.payments.web.controller.admin;


import com.samenea.common.security.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Date: 1/16/13
 * Time: 2:38 PM
 * This controller use for login
 * @Author:payam
 */
@Controller
public class LoginController {

    @RequestMapping(value="/admin/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        model.addAttribute("message", "ok");
        model.addAttribute("user", new User());
        return "admin/login";

    }

    @RequestMapping(value="/admin/loginfailed", method = RequestMethod.GET)
    public String loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return "admin/login";

    }
    @RequestMapping(value = "/admin/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "admin/login";

    }
}