package com.samenea.payments.web.controller.tracking;

import com.samenea.commons.component.model.exceptions.NotFoundException;
import com.samenea.payments.order.Order;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.tracking.TrackingSearchViewModel;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.web.model.tracking.TrackingViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Date: 1/22/13
 * Time: 3:29 PM
 *
 * @Author:payam
 */

@Controller
public class TrackingController {
    @Autowired
    OrderService orderService;
    Order order;

    @RequestMapping(value = "/tracking", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        TrackingSearchViewModel trackingSearchViewModel = new TrackingSearchViewModel();
        modelMap.addAttribute("trackingSearchViewModel", trackingSearchViewModel);
        modelMap.addAttribute("hasError", "false");
        return View.Tracking.TRACKING;
    }

    @RequestMapping(value = "/tracking", method = RequestMethod.POST)
    public String find(ModelMap modelMap,@Valid TrackingSearchViewModel trackSearchViewModel ,BindingResult result) {
        if (!result.hasErrors()) {
        try {
            order = orderService.findByOrderId(trackSearchViewModel.getTrackID());
             if(order.getCustomerInfo().getEmail().equalsIgnoreCase(trackSearchViewModel.getEmail()))
             {
                 TrackingViewModel trackingViewModel =new TrackingViewModel(order);
                 modelMap.addAttribute("trackingViewModel",trackingViewModel);
             }else
             {
                 modelMap.addAttribute("hasError", "true");
                 return View.Tracking.TRACKING;
             }
        } catch (NotFoundException notFoundException) {
            modelMap.addAttribute("hasError", "true");
            return View.Tracking.TRACKING;
        }
        return View.Tracking.ORDER;
        }
        return View.Tracking.TRACKING;
    }


}