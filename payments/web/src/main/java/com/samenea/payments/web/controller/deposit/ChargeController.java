package com.samenea.payments.web.controller.deposit;


import com.samenea.appfeature.ApplicationFeature;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.deposit.ChargeDepositViewModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Date: 1/23/13
 * Time: 5:10 PM
 *
 * @Author:payam
 */
@Controller
@ApplicationFeature("charge")
@Lazy
public class ChargeController {
    private static final Logger logger = LoggerFactory.getLogger(ChargeController.class);
    private ChargeDepositViewModel chargeDepositViewModel = new ChargeDepositViewModel();


    @Value("${chargeDeposit.maxDescriptionText}")
    String maxDescriptionText;
    @Value("${chargeDeposit.minDescriptionText}")
    String minDescriptionText;
    @Value("${chargeDeposit.minAmount}")
    private String minAmount;
    @Value("${chargeDeposit.maxAmount}")
    private String maxAmount;
    @Value("${chargeDeposit.minLengthDescription}")
    private String minLengthDescription;
    @Value("${chargeDeposit.maxLengthDescription}")
    private String maxLengthDescription;
    @Value("${chargeDeposit.minAmountText}")
    String minAmountText;
    @Value("${chargeDeposit.maxAmountText}")
    String maxAmountText;
    @Value("${chargeDeposit.is}")
    String is;

    @RequestMapping(value = "/deposit/charge", method = RequestMethod.GET)
    public String displayChargePage(ModelMap model) {
        logger.debug("Displaying charge page");
        model.addAttribute("chargeDepositViewModel", chargeDepositViewModel);
        model.addAttribute("maxAmountText", concat("max"));
        model.addAttribute("minAmountText", concat("min"));
        model.addAttribute("maxLengthDescription", maxLengthDescription);
        model.addAttribute("minLengthDescription", minLengthDescription);
        model.addAttribute("maxAmount", maxAmount);
        model.addAttribute("minAmount", minAmount);
        model.addAttribute("maxDescriptionText", maxDescriptionText);
        model.addAttribute("minDescriptionText", minDescriptionText);
        return View.Deposit.DEPOSIT_CHARGE;
    }

    private String concat(String value) {
        String sentence = null;

        if (value.equalsIgnoreCase("min")) {
            sentence = minAmountText + minAmount + is;
        } else if (value.equalsIgnoreCase("max")) {
            sentence = maxAmountText + maxAmount + is;
        }
        return sentence;
    }

}
