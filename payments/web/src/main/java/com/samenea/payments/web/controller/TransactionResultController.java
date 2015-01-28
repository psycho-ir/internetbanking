package com.samenea.payments.web.controller;

import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.samenea.banking.deposit.ChargeException;
import com.samenea.commons.component.utils.Environment;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.tracking.service.TrackingService;
import com.samenea.payments.model.DeliveryException;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.translation.TrackingTranslator;
import com.samenea.payments.web.model.ResultViewModel;
import com.samenea.payments.web.model.View;
import com.samenea.seapay.client.PaymentManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.itextpdf.text.Document;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/30/13
 * Time: 4:00 PM
 */

@Lazy
@Controller
public class TransactionResultController  {
    private static final Logger logger = LoggerFactory.getLogger(TransactionResultController.class);
    private Logger excptionLogger = LoggerFactory.getLogger(TransactionResultController.class, LoggerFactory.LoggerType.EXCEPTION);

    public static final String TRANSACTION_SUCCESS = "PAYMENT_OK";
    @Autowired
    PaymentManager paymentManager;
    @Autowired
    OrderService orderService;
    @Autowired
    TrackingService trackingService;
    @Autowired
    Environment environment;
    @Autowired
    TrackingTranslator trackingTranslator;
    @Value("${pdfTemplateView.spamEmail}")
    private String spam_email_text;
    @RequestMapping("/processTransactionResult/{orderId}")
    public String process(HttpSession session,String transactionId, @PathVariable String orderId, String result, String BANK_REFERENCE_ID, ModelMap model) {
        logger.debug("Processing transaction {} for orderId: {} reported result is {}", transactionId, orderId, result);
        if (TRANSACTION_SUCCESS.equalsIgnoreCase(result)) {
            try {
                trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.TRANSACTION_SUCCESSFUL_RETURN, transactionId));
               // trackingService.makeSynonym(orderId,transactionId);
                paymentManager.processTransaction(orderId, transactionId);
                trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.TRANSACTION_SUCCESSFUL_COMMIT, transactionId));
                trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_SUBMITTED,transactionId));
                logger.info("Transaction {} processed for orderId: {}", transactionId, orderId);
                model.addAttribute("transactionId", transactionId);
                model.addAttribute("orderId", orderId);
                model.addAttribute("referenceId",BANK_REFERENCE_ID);
                ResultViewModel resultViewModel = new ResultViewModel(orderService.findByOrderId(orderId));
                resultViewModel.setTransactionId(transactionId);
                resultViewModel.setReferenceId(BANK_REFERENCE_ID);
                model.addAttribute("resultViewModel", resultViewModel);
                model.addAttribute("spam_email_text", spam_email_text);
                session.setAttribute("transactionId", resultViewModel.getTransactionId());
                session.setAttribute("orderId",orderId);
                session.setAttribute("referenceId", resultViewModel.getReferenceId());
                return View.Deposit.RESULT;
            } catch (DeliveryException deliveryException) {
                trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_FAILED_TARANSACTION, transactionId));
                excptionLogger.error("Payment is Ok but Deposit is not charged. it needs followup", deliveryException);
                logger.error("Payment is Ok but Deposit is not charged. it needs followup, message is {}", deliveryException.getCause().getMessage());
                return View.Deposit.FAILED;
            }  catch (ChargeException chargeException){
                trackingService.record(orderId, String.format("a problem was occurred in recording simia document for transaction : %s", transactionId));
                excptionLogger.error("Payment is Ok but Deposit is not charged. it needs followup", chargeException);
                logger.error("Payment is Ok but Deposit is not charged. it needs followup, message is {}", chargeException.getCause().getMessage());
                return View.Deposit.FAILED;
            }
        } else {
            trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.TRANSACTION_PROBLEM, transactionId));
            logger.debug("Reported transaction result is: {} for transaction: {}", result, transactionId);
            model.addAttribute("transactionId", transactionId);
            return View.Deposit.FAILED;
        }
    }




}