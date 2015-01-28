package com.samenea.payments.web.controller;

import com.samenea.banking.deposit.IDeposit;
import com.samenea.banking.deposit.IDepositService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.User;
import com.samenea.payments.web.model.View;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/21/13
 * Time: 3:48 PM
 */

//@Controller
@RequestMapping("/deposits")
public class DepositFinderController {
    private static final Logger logger = LoggerFactory.getLogger(DepositFinderController.class);

    @Autowired
    private IDepositService depositService;

    @RequestMapping
    public String findAllDeposits(ModelMap modelMap) throws Exception {
        try {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User) {
                User currentUser = (User) principal;
                logger.debug("User with CIF:{} wants to see his deposits", currentUser.getCIF());
                List<IDeposit> deposits = depositService.findActiveDeposits(currentUser.getCIF());
                modelMap.addAttribute("deposits", deposits);
                return View.Deposit.All_DEPOSITS;
            }
            return "error";

        } catch (Exception e) {
            logger.warn("Exception in reading user principal.");
            throw e;
        }
    }
}
