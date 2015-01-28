package com.samenea.payments.web.controller.deposit;

import com.samenea.appfeature.ApplicationFeature;
import com.samenea.banking.customer.ICustomerService;
import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.deposit.IDepositService;
import com.samenea.banking.messaging.MessageResolver;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.tracking.service.TrackingService;
import com.samenea.commons.webmvc.controller.BaseController;
import com.samenea.commons.webmvc.model.MessageType;
import com.samenea.payments.model.DefaultDepositOrderService;
import com.samenea.payments.model.LimitExceededException;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.translation.TrackingTranslator;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.common.CustomTimeValidator;
import com.samenea.payments.web.model.deposit.ChargeDepositViewModel;
import com.samenea.payments.web.model.deposit.ConfirmDepositViewModel;
import com.samenea.seapay.client.impl.CommunicationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/30/13
 * Time: 4:07 PM
 */
@ApplicationFeature("charge")
@Scope("prototype")
@Controller(value = "depositConfirm")
public class ConfirmController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(ConfirmController.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(ConfirmController.class, LoggerFactory.LoggerType.EXCEPTION);

    public static final String SPRING_REDIRECT_PREFIX = "redirect:";
    @Value("${chargeDeposit.minLengthDescription}")
    private String minLengthDescription;
    @Value("${chargeDeposit.maxLengthDescription}")
    private String maxLengthDescription;
    @Value("${seapay.startTransactionUrl}")
    private String seapayUrl;
    @Value("${chargeDeposit.minAmount}")
    private String minAmount;
    @Value("${chargeDeposit.maxAmount}")
    private String maxAmount;
    @Value("${chargeDeposit.minAmountText}")
    String minAmountText;
    @Value("${chargeDeposit.maxAmountText}")
    String maxAmountText;
    @Value("${chargeDeposit.is}")
    String is;
    @Value("${chargeDeposit.maxDescriptionText}")
    String maxDescriptionText;
    @Value("${chargeDeposit.minDescriptionText}")
    String minDescriptionText;
    @Autowired
    ICustomerService customerService;
    @Autowired
    DefaultDepositOrderService defaultDepositOrderService;
    @Autowired
    OrderService orderService;
    @Autowired
    TrackingService trackingService;
    @Autowired
    IDepositService depositService;
    @Autowired
    TrackingTranslator trackingTranslator;
    @Autowired
    MessageResolver messageResolver;
    @Value("${startTime}")
    private String startTime;
    @Value("${endTime}")
    private String endTime;
    @Value("${timeValidation.messagepart1}")
    private String message_timeValidationـpart1;
    @Value("${timeValidation.messagepart2}")
    private String message_timeValidationـpart2;
    @Value("${timeValidation.messagepart3}")
    private String message_timeValidationـpart3;
    @Autowired
    CustomTimeValidator customTimeValidator;
    @RequestMapping(value = "/deposit/confirm", method = RequestMethod.POST)
    public String showConfirmDepositInfo(ModelMap modelMap, @Valid ChargeDepositViewModel chargeDepositViewModel, BindingResult result) {
        if (customTimeValidator.isValid(startTime, endTime)) {
            if (result.hasErrors()) {
                createModelMap(modelMap);
                return View.Deposit.DEPOSIT_CHARGE;
            } else {
                if (!isValid(Integer.parseInt(chargeDepositViewModel.getAmount()), minAmount, maxAmount)) {
                    createModelMap(modelMap);
                    modelMap.addAttribute("amountSizeError", "true");
                    return View.Deposit.DEPOSIT_CHARGE;
                }
                if (!isValid(chargeDepositViewModel.getDescription().length(), minLengthDescription, maxLengthDescription)) {
                    createModelMap(modelMap);
                    modelMap.addAttribute("descriptionSizeError", "true");
                    return View.Deposit.DEPOSIT_CHARGE;
                }
                try {
                    String depositNumber = chargeDepositViewModel.getDepositNumber1() + "." + chargeDepositViewModel.getDepositNumber2();
                    if (isValidDepositNumber(depositNumber)) {
                        ConfirmDepositViewModel confirmDepositViewModel =
                                new ConfirmDepositViewModel(chargeDepositViewModel, customerService.findCustomersOfDeposit(depositNumber));
                        modelMap.addAttribute("confirmDepositViewModel", confirmDepositViewModel);
                    } else {
                        createModelMap(modelMap);
                        modelMap.addAttribute("depositNumberError", "true");
                        return View.Deposit.DEPOSIT_CHARGE;
                    }
                } catch (RuntimeException ex) {
                    logger.error("Could not connect to simia. {}", ex.getMessage());
                    return SPRING_REDIRECT_PREFIX + "/failed";
                }
            }
            return View.Deposit.DEPOSIT_CONFIRM;
        } else {


            modelMap.addAttribute("message_time_error",message_timeValidationـpart1+startTime+message_timeValidationـpart2+endTime+message_timeValidationـpart3);
            return View.Deposit.DEPOSIT_CHARGE;

        }
    }

    private void createModelMap(ModelMap modelMap) {
        modelMap.addAttribute("maxAmountText", concat("max"));
        modelMap.addAttribute("minAmountText", concat("min"));
        modelMap.addAttribute("maxAmount", maxAmount);
        modelMap.addAttribute("minAmount", minAmount);
        modelMap.addAttribute("maxLengthDescription", maxLengthDescription);
        modelMap.addAttribute("minLengthDescription", minLengthDescription);
        modelMap.addAttribute("maxDescriptionText", maxDescriptionText);
        modelMap.addAttribute("minDescriptionText", minDescriptionText);
    }

    @RequestMapping(value = "/deposit/redirectToBank")
    public String redirect(ConfirmDepositViewModel confirmDepositViewModel, BindingResult result) {
        try {
            if (isValidConfirmModel(confirmDepositViewModel)) {
                CustomerInfo customerInfo = new CustomerInfo(confirmDepositViewModel.getPhoneNumber(), confirmDepositViewModel.getEmail());
                String orderId = defaultDepositOrderService.createCheckedOutDepositChargeOrder(confirmDepositViewModel.getNumberDeposit(),
                        confirmDepositViewModel.getAmount(), customerInfo).getOrderId();

                trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.ORDER_CREATED, confirmDepositViewModel.toString()));
                trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.ORDER_REDIRECTED, SPRING_REDIRECT_PREFIX + seapayUrl + orderId + "/"));
                return SPRING_REDIRECT_PREFIX + seapayUrl + orderId + "/";

            } else {
                return View.Deposit.DEPOSIT_CHARGE;
            }
        } catch (ChargeException e) {
            String message = messageResolver.resolve(e);
            logger.error("Creating order failed. Charge for this deposit is not possible.{}", message);
            exceptionLogger.error("Creating order failed. Charge for this deposit is not possible.", e);
            return View.Deposit.DEPOSIT_CONFIRM;
        } catch (LimitExceededException e) {
            addMessage(messageSource.getMessage("errors.chargeLimitPerDayExceeded", new Integer[]{e.getLimit()}, locale), MessageType.ERROR);
            logger.debug("chargeLimit exceeded. {}", e.getMessage());
            exceptionLogger.debug("chargeLimit exceeded.", e);
            return View.Default.HOME_PAGE;
        }
    }

    private boolean isValidConfirmModel(ConfirmDepositViewModel confirmDepositViewModel) {
        if (confirmDepositViewModel.getNumberDeposit() != null && confirmDepositViewModel.getAmountDepositNumeric() != null &&
                confirmDepositViewModel.getEmail() != null && confirmDepositViewModel.getPhoneNumber() != null && confirmDepositViewModel.getAmount() != 0) {
            return true;
        }
        return false;
    }

    @ExceptionHandler({CommunicationException.class})
    private String handleCommunicationException(CommunicationException communicationException) {

        logger.error("Could not connect to seapay. (communicationException){}", communicationException.getMessage());
        return SPRING_REDIRECT_PREFIX + "/failed";
    }

    @RequestMapping("/failed")
    public String failed(ModelMap modelMap) {

        return View.Deposit.FAILED;

    }

    @RequestMapping("/displayOrder/{orderId}")
    public String displayOrder(ModelMap modelMap, @PathVariable String orderId) {
        ConfirmDepositViewModel confirmDepositViewModel = null;
        try {
            confirmDepositViewModel =
                    new ConfirmDepositViewModel(orderService.findByOrderId(orderId));
            modelMap.addAttribute("confirmDepositViewModel", confirmDepositViewModel);
        } catch (RuntimeException ex) {
            logger.error("Invalid OrderId. {}", ex.getMessage());
            trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.ORDER_DISPLAY_PROBLEM));
            return SPRING_REDIRECT_PREFIX + "/failed";
        }
        trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.ORDER_DISPLAYED, confirmDepositViewModel.toString()));
        return View.Deposit.DEPOSIT_CONFIRM;
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

    private boolean isValidDepositNumber(String depositNumber) {
        try {
            if (depositNumber.length() > 2)
                return depositService.isValidForCharging(depositNumber);
        } catch (com.samenea.commons.component.model.exceptions.NotFoundException excption) {
            return false;
        }
        return false;
    }

    public boolean isValid(int value, String min, String max) {
        try {

            if (Integer.parseInt(min) <= value && value <= Integer.parseInt(max))
                return true;

        } catch (RuntimeException e) {
            return false;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}