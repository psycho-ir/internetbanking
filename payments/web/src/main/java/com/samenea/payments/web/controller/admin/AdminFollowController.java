package com.samenea.payments.web.controller.admin;

import com.samenea.commons.tracking.model.Track;
import com.samenea.commons.tracking.service.TrackingService;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.follow.FollowSearchViewModel;
import com.samenea.payments.web.model.follow.FollowSearchViewModel;
import com.samenea.payments.web.model.follow.FollowViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/19/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Lazy
@Controller
public class AdminFollowController {

    @Autowired
    TrackingService trackingService;

    @RequestMapping(value = "/admin/follow", method={RequestMethod.POST})
    public String findTracks(FollowSearchViewModel followSearchViewModel, ModelMap trackingMap, BindingResult result){
        List<Track> tracks = trackingService.findTracks(followSearchViewModel.getfollowId());
        List<FollowViewModel>  followViewModels = new ArrayList<FollowViewModel>();
        for (Track track : tracks) {
            followViewModels.add(new FollowViewModel(track));
        }

        trackingMap.addAttribute("followViewModel", followViewModels);
        return View.Admin.Follow;
    }

    @RequestMapping(value = "/admin/follow", method={RequestMethod.GET})
    public String findTracks(ModelMap modelMap){
        modelMap.addAttribute("followViewModel", new ArrayList<FollowViewModel>());
        modelMap.addAttribute("followSearchViewModel", new FollowSearchViewModel());
        return  View.Admin.Follow;
    }
}
