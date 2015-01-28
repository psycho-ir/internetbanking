package com.samenea.payments.translation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;



/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/25/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TrackingTranslator {

    public static final String ORDER_CREATED = "ORDER_CREATED";
    public static final String ORDER_DISPLAYED = "ORDER_DISPLAYED";
    public static final String ORDER_DISPLAY_PROBLEM = "ORDER_DISPLAY_PROBLEM";
    public static final String TRANSACTION_SUCCESSFUL_RETURN = "TRANSACTION_SUCCESSFUL_RETURN";
    public static final String TRANSACTION_SUCCESSFUL_COMMIT = "TRANSACTION_SUCCESSFUL_COMMIT";
    public static final String SIMIA_DOCUMENT_SUBMITTED = "SIMIA_DOCUMENT_SUBMITTED";
    public static final String SIMIA_DOCUMENT_FAILED_TARANSACTION = "SIMIA_DOCUMENT_FAILED_TRANSACTION";
    public static final String SIMIA_DOCUMENT_FAILED_ORDER = "SIMIA_DOCUMENT_FAILED_ORDER";
    public static final String TRANSACTION_PROBLEM = "TRANSACTION_PROBLEM";
    public static final String ORDER_REDIRECTED = "ORDER_REDIRECTED";
    public static final String LOAN_CONFIRMED = "LOAN_CONFIRMED";



    @Value("${order.created}")
    String orderCreated;

    @Value("${order.displayed}")
    String orderDisplayed;

    @Value("${order.display.problem}")
    String orderDisplayProblem;

    @Value("${transaction.successful.return}")
    String transactionSuccessfulReturn;

    @Value("${transaction.successful.commit}")
    String transactionSuccessfulCommit;

    @Value("${simia.document.submitted}")
    String simiaDocumentSubmitted;

    @Value("${simia.document.failed.transaction}")
    String simiaDocumentFailedForTransaction;

    @Value("${simia.document.failed.order}")
    String simiaDocumentFailedForOrder;

    @Value("${transaction.problem}")
    String transactionProblem;

    @Value("${order.redirected}")
    String orderRedirected;

    @Value("${loan.confirmed}")
    String loanConfirmed;

    Map<String, String> allMessages;

    public void createMessages(){
        allMessages = new HashMap<String, String>();
        Assert.notNull(orderCreated);
        Assert.notNull(orderDisplayed);
        Assert.notNull(orderDisplayProblem);
        Assert.notNull(transactionSuccessfulReturn);
        Assert.notNull(transactionSuccessfulCommit);
        Assert.notNull(simiaDocumentSubmitted);
        Assert.notNull(simiaDocumentFailedForTransaction);
        Assert.notNull(simiaDocumentFailedForOrder);
        Assert.notNull(transactionProblem);
        Assert.notNull(orderRedirected);
        Assert.notNull(loanConfirmed);

        allMessages.put(ORDER_CREATED, orderCreated);
        allMessages.put(ORDER_DISPLAYED, orderDisplayed);
        allMessages.put(ORDER_DISPLAY_PROBLEM, orderDisplayProblem);
        allMessages.put(TRANSACTION_SUCCESSFUL_RETURN, transactionSuccessfulReturn);
        allMessages.put(TRANSACTION_SUCCESSFUL_COMMIT, transactionSuccessfulCommit);
        allMessages.put(SIMIA_DOCUMENT_SUBMITTED, simiaDocumentSubmitted);
        allMessages.put(SIMIA_DOCUMENT_FAILED_TARANSACTION, simiaDocumentFailedForTransaction);
        allMessages.put(SIMIA_DOCUMENT_FAILED_ORDER, simiaDocumentFailedForOrder);
        allMessages.put(TRANSACTION_PROBLEM, transactionProblem);
        allMessages.put(ORDER_REDIRECTED, orderRedirected);
        allMessages.put(LOAN_CONFIRMED, loanConfirmed);

    }

    public String translate(String messageCode, String... args){
        if(allMessages == null){
            createMessages();
        }
        return String.format(allMessages.get(messageCode), args);
    }



}
