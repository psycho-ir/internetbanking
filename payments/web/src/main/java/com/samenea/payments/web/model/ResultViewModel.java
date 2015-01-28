package com.samenea.payments.web.model;

import com.samenea.commons.component.utils.persian.DateUtil;
import com.samenea.commons.component.utils.persian.NumberToWritten;
import com.samenea.payments.model.DepositParams;
import com.samenea.payments.model.loan.InstallmentParams;
import com.samenea.payments.order.Order;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Date: 1/30/13
 * Time: 11:19 AM
 *
 * @Author:payam
 */
public class ResultViewModel {

    private String transactionId;
    private String amount;
    private String depositNumber;
    private String loanNumber;
    private String amountLetter;
    private String typeOfResult;
    private String orderState;
    private String date;

    public String getReferenceId() {
        return referenceId;
    }

    private String referenceId;

    public String getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(String installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    private String installmentNumber;
    private PdfTemplateView pdfTemplateView;
    private static final String INSTALLMENT_PAY_PRODUCT_NAME = "InstallmentPay";
    public static final String DEPOSIT_CHARGE_PRODUCT_NAME = "DepositCharge";
    public ResultViewModel(Order order) {
        if(order!=null){
            DepositParams depositParams ;
            InstallmentParams installmentParams;
            this.orderState=order.getStatus().toString();
            this.date= DateUtil.toString(order.getLastUpdateDate());
            if(order.getLineItems().get(0).getProductSpec().getProductName().equalsIgnoreCase(INSTALLMENT_PAY_PRODUCT_NAME))
            {
                installmentParams = InstallmentParams.fromProductSpec(order.getLineItems().get(0).getProductSpec());
                this.loanNumber=installmentParams.getLoanNumber();
                this.installmentNumber=installmentParams.getInstallmentNumber();

                typeOfResult=INSTALLMENT_PAY_PRODUCT_NAME;

            }
            else if(order.getLineItems().get(0).getProductSpec().getProductName().equalsIgnoreCase(DEPOSIT_CHARGE_PRODUCT_NAME))
            {
                typeOfResult=DEPOSIT_CHARGE_PRODUCT_NAME;
                depositParams = DepositParams.fromCriteriaString(order.getLineItems().get(0).getProductSpec().getCriteria());
                this.depositNumber=depositParams.getDepositNumber();

            }
             this.amount=String.valueOf(order.getTotalPrice());
             this.amountLetter= NumberToWritten.convert(order.getTotalPrice());

        }
        this.pdfTemplateView=new PdfTemplateView();
    }
    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }
    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeOfResult() {
        return typeOfResult;
    }

    public void setTypeOfResult(String typeOfResult) {
        this.typeOfResult = typeOfResult;
    }
    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getDepositNumber() {
        return depositNumber;
    }
    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }
    public String getAmountLetter() {
        return amountLetter;
    }
    public void setAmountLetter(String amountLetter) {
        this.amountLetter = amountLetter;
    }

    public PdfTemplateView getPdfTemplateView() {
        return pdfTemplateView;
    }

    public void setPdfTemplateView(PdfTemplateView pdfTemplateView) {
        this.pdfTemplateView = pdfTemplateView;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
