package com.samenea.payments.web.controller.loan;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.web.model.View;
import com.samenea.appfeature.ApplicationFeature;
import com.samenea.payments.web.model.common.CustomTimeValidator;
import com.samenea.payments.web.model.loan.LoanInstalmentViewModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Date: 2/12/13
 * Time: 9:39 AM
 *
 * @Author:payam
 */
@ApplicationFeature("loan")
@Controller
public class PayController {

    private static final Logger logger = LoggerFactory.getLogger(PayController.class);
    private LoanInstalmentViewModel loanInstalmentViewModel = new LoanInstalmentViewModel();
    @RequestMapping(value = "/loan/pay", method = RequestMethod.GET)
    public String displayInstalmentPage(ModelMap model) {


        logger.debug("Displaying loan page");
        model.addAttribute("loanInstalmentViewModel", loanInstalmentViewModel);
        return View.Loan.LOAN_PAY;
    }




}
