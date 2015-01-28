package com.samenea.payments.web.controller;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.tracking.service.TrackingService;
import com.samenea.payments.model.DeliveryException;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.translation.TrackingTranslator;
import com.samenea.payments.web.model.View;
import com.samenea.seapay.client.PaymentManager;
import com.samenea.seapay.client.impl.CommunicationException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author: Soroosh Sarabadani
 * Date: 2/9/13
 * Time: 3:09 PM
 */
@Lazy
@Controller
public class PostponedOrdersController {
    private static final Logger logger = LoggerFactory.getLogger(PostponedOrdersController.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(PostponedOrdersController.class, LoggerFactory.LoggerType.EXCEPTION);
    public static final String SPRING_REDIRECT_PREFIX = "redirect:";
    @Autowired
    private OrderService orderService;
    @Autowired()
    private PaymentManager paymentManager;
    @Value("${postponed.success}")
    private String postponedMessage;
    @Autowired
    TrackingService trackingService;
    @Autowired
    TrackingTranslator trackingTranslator;

    @RequestMapping("/order/postponeds")
    public String showOrders(ModelMap modelMap) {

        final List<Order> postponedOrders = orderService.findByStatus(Order.Status.POSTPONED);
        modelMap.addAttribute("orders", postponedOrders);
        return View.Order.POSTPONEDS;
    }

    @RequestMapping("/order/resolve/{orderId}")
    public String resolvePostponedOrder(RedirectAttributes redirectAttributes, @PathVariable String orderId) {
        final Order order = orderService.findByOrderId(orderId);
        try {
            paymentManager.processTransaction(order.getOrderId(), order.getReceipt().getReceiptId());
        } catch (CommunicationException e) {
            logger.warn("CommunicationException Occured in PostponedOrdersController. {}", e.getMessage());
            exceptionLogger.warn("CommunicationException Occured in PostponedOrdersController.", e);
            return View.Excption.GENERIC;
        } catch (DeliveryException deliveryException) {
            trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_FAILED_ORDER, orderId));
            exceptionLogger.error("Payment is Ok but Deposit is not charged. it needs followup", deliveryException);
            logger.error("Payment is Ok but Deposit is not charged. it needs followup, message is {}", deliveryException.getCause().getMessage());
            return View.Deposit.FAILED;
        } catch (ChargeException chargeException) {
            trackingService.record(orderId, trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_FAILED_ORDER, orderId));
            exceptionLogger.error("Payment is Ok but Deposit is not charged. it needs followup", chargeException);
            logger.error("Payment is Ok but Deposit is not charged. it needs followup, message is {}", chargeException.getCause().getMessage());
            return View.Deposit.FAILED;
        }
        redirectAttributes.addFlashAttribute("postponedMessage", postponedMessage);
        return SPRING_REDIRECT_PREFIX + "/order/postponeds";
    }
}
