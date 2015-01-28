package com.samenea.payments.web.controller.audit;

import com.samenea.payments.web.model.follow.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 4/8/13
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
@Lazy
@Controller
public class AuditController {
    @RequestMapping(value = "audit/cl", method={RequestMethod.GET})
    public String findTracks(ModelMap modelMap){

        return "audit/cl";
    }

    @RequestMapping(value = "audit/vs", method={RequestMethod.GET})
    public String viewTracks(ModelMap modelMap){

        return "audit/vs";
    }
}
