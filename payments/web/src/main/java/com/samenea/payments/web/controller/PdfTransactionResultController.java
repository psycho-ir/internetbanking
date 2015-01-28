package com.samenea.payments.web.controller;


import com.samenea.payments.order.Order;
import com.samenea.payments.order.OrderService;
import com.samenea.payments.web.model.PdfTemplateView;
import com.samenea.payments.web.model.ResultViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 3/13/13
 * Time: 11:00 AM
 *
 * @Author:payam
 */
@Controller()
public class PdfTransactionResultController extends AbstractController {
    private static final String ORDER_STATUS_MODIFIABLE = "MODIFIABLE";
    private static final String ORDER_STATUS_CHECKED_OUT = "CHECKED_OUT";
    private static final String ORDER_STATUS_POSTPONED = "POSTPONED";
    private static final String ORDER_STATUS_DELIVERED= "DELIVERED";
    private static final String ORDER_STATUS_CANCELED = "CANCELED";
    @Autowired
    OrderService orderService;
    @Value("${pdfTemplateView.depositTitles}")
    private String depositTitles;
    @Value("${pdfTemplateView.loanTitles}")
    private String loanTitles;
    @Value("${pdfTemplateView.depositDescription}")
    private String depositDescription;
    @Value("${pdfTemplateView.loanDescription}")
    private String loanDescription;
    @Value("${pdfTemplateView.samenTitles}")
    private String samenTitles;
    @Value("${pdfTemplateView.trackingTitles}")
    private String trackingTitles;
    @Value("${pdfTemplateView.amountText}")
    private String amountText;
    @Value("${pdfTemplateView.trackingLink}")
    private String trackingLink;
    @Value("${pdfTemplateView.orderState}")
    private String orderState;
    @Value("${pdfTemplateView.dateText}")
    private String dateText;
    @Value("${order.MODIFIABLE}")
    private String order_MODIFIABLE;
    @Value("${order.CHECKED_OUT}")
    private String order_CHECKED_OUT;
    @Value("${order.POSTPONED}")
    private String order_POSTPONED;
    @Value("${order.DELIVERED}")
    private String order_DELIVERED;
    @Value("${order.CANCELED}")
    private String order_CANCELED;
    @Value("${pdfTemplateView.spamEmail}")
    private String spam_email_text;
    @Value("${pdfTemplateView.installmentNumberText}")
    private String installmentNumberText;
    @Value("${pdfTemplateView.rial}")
    private String rial;
    @Value("${pdfBankTransactionReferenceId}")
    private String referenceId;
    @Override
    @RequestMapping("/revenuereport")
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if((request.getSession().getAttribute("transactionId")!=null) && (request.getSession().getAttribute("orderId")!=null)
                && (request.getSession().getAttribute("referenceId")!=null)) {
        String transactionId = request.getSession().getAttribute("transactionId").toString();
              String orderId = request.getSession().getAttribute("orderId").toString();
          String referenceId = request.getSession().getAttribute("referenceId").toString();
        if((!transactionId.equals("")) && (!orderId.equals(""))){
            ResultViewModel resultViewModel = new ResultViewModel(orderService.findByOrderId(orderId));
            resultViewModel.setOrderState(getPersianOrderStatus(resultViewModel.getOrderState()));
            resultViewModel.setPdfTemplateView(createTemplate());
            resultViewModel.setTransactionId(orderId);
            resultViewModel.setReferenceId(referenceId);
            return new ModelAndView("PdfRevenueSummary","resultViewModel",resultViewModel);

        }
        }
        return  null;
    }

     private PdfTemplateView createTemplate(){
         PdfTemplateView pdfTemplateView=new PdfTemplateView(depositTitles,loanTitles,depositDescription,loanDescription,samenTitles,trackingTitles,amountText,trackingLink,orderState,dateText,spam_email_text,installmentNumberText,rial,referenceId);
         return pdfTemplateView;
     }
    private String getPersianOrderStatus(String orderStatus){
        if (orderStatus.equals(ORDER_STATUS_MODIFIABLE)) {
            return order_MODIFIABLE;
        }else if(orderStatus.equals(ORDER_STATUS_CANCELED)) {
            return order_CANCELED;}
        else if(orderStatus.equals(ORDER_STATUS_CHECKED_OUT)) {
            return order_CHECKED_OUT;}
        else if(orderStatus.equals(ORDER_STATUS_POSTPONED)) {
            return order_POSTPONED;}
        else if(orderStatus.equals(ORDER_STATUS_DELIVERED)) {
            return order_DELIVERED;}
       return "";
    }


}
