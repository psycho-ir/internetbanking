package com.samenea.payments.web.controller.loan;

import com.samenea.banking.loan.ILoanService;
import com.samenea.banking.loan.InstallmentPaymentException;
import com.samenea.banking.messaging.MessageResolver;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.tracking.service.TrackingService;
import com.samenea.commons.webmvc.controller.BaseController;
import com.samenea.commons.webmvc.model.Message;
import com.samenea.commons.webmvc.model.MessageType;
import com.samenea.payments.model.LimitExceededException;
import com.samenea.payments.model.loan.LoanOrderService;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.ProductSpec;
import com.samenea.payments.translation.TrackingTranslator;
import com.samenea.payments.web.model.View;
import com.samenea.appfeature.ApplicationFeature;
import com.samenea.payments.web.model.common.CustomTimeValidator;
import com.samenea.payments.web.model.loan.ConfirmLoanInstallmentViewModel;
import com.samenea.payments.web.model.loan.LoanInstalmentViewModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.samenea.payments.order.OrderService;

import javax.validation.Valid;

/**
 * Date: 2/12/13
 * Time: 2:35 PM
 *
 * @Author:payam
 */
@Controller(value = "loanConfirm")
@Scope("prototype")
@ApplicationFeature("loan")
public class ConfirmController extends BaseController{
    public static final String SPRING_REDIRECT_PREFIX = "redirect:";
    @Value("${seapay.startTransactionUrl}")
    private String seapayUrl;
    @Value("${isNotValid.loanNumber}")
    private String isNotValidLoanNumberMessage;
    private Logger logger = LoggerFactory.getLogger(ConfirmController.class);
    @Autowired
    private ILoanService loanService;
    @Autowired
    private LoanOrderService loanOrderService;
    @Autowired
    OrderService orderService;
    @Autowired
    MessageResolver messageResolver;
    @Autowired
    TrackingService trackingService;
    @Autowired
    TrackingTranslator trackingTranslator;
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
    @RequestMapping(value = "/loan/confirm", method = RequestMethod.POST)
    public String showConfirmLoanInstalment(ModelMap modelMap, @Valid LoanInstalmentViewModel loanInstalmentViewModel,
                                            BindingResult result) {


        if (customTimeValidator.isValid(startTime, endTime)) {
            if (result.hasErrors()) {

                return View.Loan.LOAN_PAY;
            } else {
                ConfirmLoanInstallmentViewModel confirmLoanInstallmentViewModel = new ConfirmLoanInstallmentViewModel(
                        loanInstalmentViewModel.getNumber(), loanService, loanInstalmentViewModel.getEmail(), loanInstalmentViewModel.getPhoneNumber());
                modelMap.addAttribute("confirmLoanInstallmentViewModel", confirmLoanInstallmentViewModel);
                return View.Loan.LOAN_CONFIRM;
            }
        } else{
            modelMap.addAttribute("message_time_error",message_timeValidationـpart1+startTime+message_timeValidationـpart2+endTime+message_timeValidationـpart3);

            return View.Loan.LOAN_PAY;

        }


    }

    @RequestMapping(value = "/loan/redirectToBank")
    public String redirect(ConfirmLoanInstallmentViewModel confirmLoanInstallmentViewModel, BindingResult result) {

        ProductSpec productSpec = null;
        try {
            productSpec = loanOrderService.createInstallmentPaySpec(confirmLoanInstallmentViewModel.getLoanNumber());
        } catch (InstallmentPaymentException installmentPaymentException) {
            String message = messageResolver.resolve(installmentPaymentException);
            logger.error("Error in Installment Payment. {}", message);

            return View.Excption.GENERIC;
        } catch (LimitExceededException e) {
            logger.warn("Number of payed installment can not exceed limit");
            final String message = messageSource.getMessage("loan.installmentPayCountLimitError", new Integer[]{e.getLimit()}, locale);
            messages.add(new Message(message, MessageType.ERROR));
            return View.Default.HOME_PAGE;
        }
        CustomerInfo customerInfo = new CustomerInfo(confirmLoanInstallmentViewModel.getPhoneNumber(), confirmLoanInstallmentViewModel.getEmail());
        String orderId = orderService.createCheckedOutOrder(customerInfo, productSpec, Integer.valueOf(
                confirmLoanInstallmentViewModel.getInstallmentspayableAmount())).getOrderId();
        trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.LOAN_CONFIRMED, confirmLoanInstallmentViewModel.toString()));

        return SPRING_REDIRECT_PREFIX + seapayUrl + orderId + "/";

    }

}
