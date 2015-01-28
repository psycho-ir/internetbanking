package com.samenea.payments.service.messaging;

import com.samenea.banking.simia.model.SimiaUtils;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.component.utils.persian.NumberUtil;
import com.samenea.payments.model.DepositParams;
import com.samenea.payments.model.loan.InstallmentParams;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.ProductSpec;
import com.samenea.payments.order.UserInfo;
import com.samenea.payments.order.event.OrderStatusChanged;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Soroosh Sarabadani
 * Date: 3/17/13
 * Time: 12:32 PM
 */

@Service
public class OrderChangedEmailSender implements ApplicationListener<OrderStatusChanged> {
    private Logger logger = LoggerFactory.getLogger(OrderChangedEmailSender.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(OrderChangedEmailSender.class, LoggerFactory.LoggerType.EXCEPTION);
    @Autowired
    private JavaMailSender mailSender;
    @Value("${mail.emailAddress}")
    private String emailAddess;
    @Value("${mail.subject}")
    private String subject;
    @Value("${mail.sendingIsEnabled}")
    private boolean emailSendingIsEnabled;
    @Value("${mail.threads.count}")
    private int numberOfThreads;

    @Value("${mail.template.installment}")
    private String installmentTxt;
    @Value("${mail.template.loan}")
    private String loanTxt;
    @Value("${mail.template.byname}")
    private String bynameTxt;
    @Value("${mail.template.postponed}")
    private String postponedTxt;
    @Value("${mail.temlate.delivered}")
    private String deliveredTxt;
    @Value("${mail.template.rial}")
    private String rialTxt;
    @Value("${mail.template.amount}")
    private String amountTxt;
    @Value("${mail.template.date}")
    private String dateTxt;
    @Value("${mail.template.orderid}")
    private String orderidTxt;
    @Value("${mail.template.samen}")
    private String samenTxt;
    @Value("${mail.template.payment}")
    private String paymentTxt;
    @Value("${mail.template.transfer}")
    private String transferTxt;

    public static final String INSTALLMENT_TXT = "INSTALLMENT_TXT";
    public static final String lOAN_TXT = "lOAN_TXT";
    public static final String BYNAME_TXT = "BYNAME_TXT";
    public static final String POSTPONED_TXT = "POSTPONED_TXT";
    public static final String DELIVERED_TXT = "DELIVERED_TXT";
    public static final String RIAL_TXT = "RIAL_TXT";
    public static final String AMOUNT_TXT = "AMOUNT_TXT";
    public static final String DATE_TXT = "DATE_TXT";
    public static final String ORDERID_TXT = "ORDERID_TXT";
    public static final String SAMEN_TXT = "SAMEN_TXT";
    public static final String PAYMENT_TXT = "PAYMENT_TXT";
    public static final String TRANSFER_TXT = "TRANSFER_TXT";

    private ExecutorService executorService;
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String DEPOSIT_NUMBER = "DEPOSIT_NUMBER";
    public static final String AMOUNT = "AMOUNT";
    public static final String DATE = "DATE";
    public static final String ORDER_ID = "ORDER_ID";
    public static final String LOAN_NUMBER = "LOAN_NUMBER";
    public static final String INSTALLMENT_NUMBER = "INSTALLMENT_NUMBER";
    public static final String TEMPLATE_PATH = "template.ftl";
    private Configuration configuration = new Configuration();
    private Template template;

    @PostConstruct
    public void init() throws IOException {

        configuration.setClassForTemplateLoading(OrderChangedEmailSender.class, "/");
        executorService = Executors.newFixedThreadPool(numberOfThreads);

    }

    @Override
    public void onApplicationEvent(OrderStatusChanged event) {
        if (emailSendingIsEnabled) {
            try {
                template = configuration.getTemplate(TEMPLATE_PATH);
            } catch (IOException e) {
                logger.error("cannot find {}", TEMPLATE_PATH);
                exceptionLogger.error("Error in create email template", e);
            }
            final String installmentPayProductName = InstallmentParams.INSTALLMENT_PAY_PRODUCT_NAME;
            final String depositChargeProductName = DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME;
            final Order order = event.getOrder();
            final ProductSpec productSpec = event.getOrder().getLineItems().get(0).getProductSpec();
            if (productSpec.getProductName().equals(installmentPayProductName)) {
                handleInstallmentMessageSending(order);
            } else if (productSpec.getProductName().equals(depositChargeProductName)) {
                handleDepositMessageSending(order);
            } else {
                throw new RuntimeException(String.format("Inconsistent Order created. Order:%s", order.toString()));
            }

        } else {
            logger.debug("Email sending is Disabled so email will not be sent.");
        }
    }

    private void handleDepositMessageSending(Order order) {
        final ProductSpec productSpec = order.getLineItems().get(0).getProductSpec();
        final UserInfo deliveryInfo = productSpec.getDeliveryInfo();
        final DepositParams depositParams = DepositParams.fromProductSpec(productSpec);
        Map<String, String> values = new HashMap<String, String>();
        values= initMap(values);
        values.put(FIRST_NAME, deliveryInfo.getFirstName());
        values.put(LAST_NAME, deliveryInfo.getLastName());
        values.put(DEPOSIT_NUMBER, depositParams.getDepositNumber());
        values.put(AMOUNT, NumberUtil.convertDigits(seperate(String.valueOf(depositParams.getAmount()))));
        values.put(DATE, SimiaUtils.getCurrentDate(order.getLastUpdateDate()));
        values.put(ORDER_ID, order.getOrderId());

        String message = null;
        StringWriter result = new StringWriter();
        values.put("resultType", order.getStatus().name());
        values.put("type", DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME);

        try {
            template.process(values, result);
            message = result.toString();
        } catch (TemplateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
        }
        sendMail(message, order);


    }

    private void handleInstallmentMessageSending(Order order) {
        final ProductSpec productSpec = order.getLineItems().get(0).getProductSpec();
        final UserInfo deliveryInfo = productSpec.getDeliveryInfo();
        final InstallmentParams installmentParams = InstallmentParams.fromProductSpec(productSpec);
        Map<String, String> values = new HashMap<String, String>();
        values= initMap(values);
        values.put(FIRST_NAME, deliveryInfo.getFirstName());
        values.put(LAST_NAME, deliveryInfo.getLastName());
        values.put(LOAN_NUMBER, installmentParams.getLoanNumber());
        values.put(INSTALLMENT_NUMBER, installmentParams.getInstallmentNumber());
        values.put(AMOUNT, NumberUtil.convertDigits(seperate(String.valueOf(installmentParams.getAmount()))));
        values.put(DATE, SimiaUtils.getCurrentDate(order.getLastUpdateDate()));
        values.put(ORDER_ID, order.getOrderId());
        String message = null;
        StringWriter result = new StringWriter();
        values.put("resultType", order.getStatus().name());
        values.put("type", InstallmentParams.INSTALLMENT_PAY_PRODUCT_NAME);

        try {
            template.process(values, result);
            message = result.toString();
        } catch (TemplateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
        }
        sendMail(message, order);
    }

    private void sendMail(final String message, final Order order) {
        final CustomerInfo customerInfo = order.getCustomerInfo();
        Thread sendMailThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final MimeMessagePreparator htmlMessage = MessageFactory.createHtmlMessage(emailAddess, customerInfo.getEmail(), subject, message);
                try {
                    logger.info("Email is sending for customer:{} for order:{} with status:{} ", customerInfo.getEmail(), order.getOrderId(), order.getStatus());
                    mailSender.send(htmlMessage);
                    logger.info("Email sent for customer:{} for order:{} with status:{} ", customerInfo.getEmail(), order.getOrderId(), order.getStatus());
                } catch (Exception e) {
                    logger.info("Sending email for customer:{} for order:{} with status:{} failed", customerInfo.getEmail(), order.getOrderId(), order.getStatus());
                    exceptionLogger.error("Error in sending email", e);
                }

            }
        }, "EmailSenderThread");
        logger.info("Sending email for customer:{} is queued for sending order:{} with status:{}", customerInfo.getEmail(), order.getOrderId(), order.getStatus());
        if (executorService.isShutdown()) {
            logger.info("Email executorService is shut downed so email won't be sent to customer:{} ", customerInfo.getEmail());
            return;
        }
        executorService.submit(sendMailThread);

    }

    private  Map<String, String>initMap( Map<String, String> values){
        values.put(INSTALLMENT_TXT,installmentTxt);
        values.put(lOAN_TXT,loanTxt);
        values.put(BYNAME_TXT,bynameTxt);
        values.put(POSTPONED_TXT,postponedTxt);
        values.put(DELIVERED_TXT,deliveredTxt);
        values.put(RIAL_TXT,rialTxt);
        values.put(AMOUNT_TXT,amountTxt);
        values.put(DATE_TXT,dateTxt);
        values.put(SAMEN_TXT,samenTxt);
        values.put(TRANSFER_TXT,transferTxt);
        values.put(ORDERID_TXT,orderidTxt);
        return values;
    }

    private String seperate(String amountNumber) {
        char[] myNumber = amountNumber.toCharArray();
        String myResult = "";
        for (int i = amountNumber.length() - 1; i >= 0; i--) {
            myResult = myNumber[i] + myResult;
            if ((myNumber.length - i) % 3 == 0 & i > 0)
                myResult = "," + myResult;
        }

        return myResult;

    }


}
