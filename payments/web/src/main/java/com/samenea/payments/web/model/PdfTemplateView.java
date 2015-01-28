package com.samenea.payments.web.model;

/**
 * Date: 3/31/13
 * Time: 5:50 PM
 *
 * @Author:payam
 */
public class PdfTemplateView {

    public String getReferenceId() {
        return referenceId;
    }

    private String referenceId;
    private String depositTitles;
    private String loanTitles;
    private String depositDescription;
    private String loanDescription;
    private String samenTitles;
    private String trackingTitles;
    private String amountText;
    private String trackingLink;
    private String orderState;
    private String dateText;
    private String spam_email_text;

    public String getRial() {
        return rial;
    }

    public void setRial(String rial) {
        this.rial = rial;
    }

    private String rial;

    public String getInstallment_number() {
        return installment_number;
    }

    public void setInstallment_number(String installment_number) {
        this.installment_number = installment_number;
    }

    private String installment_number;

    public String getSpam_email_text() {
        return spam_email_text;
    }

    public void setSpam_email_text(String spam_email_text) {
        this.spam_email_text = spam_email_text;
    }

    public PdfTemplateView( String depositTitles, String loanTitles, String depositDescription, String loanDescription, String samenTitles, String trackingTitles, String amountText, String trackingLink,String orderState,String dateText,String spam_email_text ,String installment_number,String rial,String referenceId) {

        this.depositTitles = depositTitles;
        this.loanTitles = loanTitles;
        this.depositDescription = depositDescription;
        this.loanDescription = loanDescription;
        this.samenTitles = samenTitles;
        this.trackingTitles = trackingTitles;
        this.amountText = amountText;
        this.trackingLink = trackingLink;
        this.orderState=orderState;
        this.dateText=dateText;
        this.installment_number=installment_number;
        this.spam_email_text=spam_email_text;
        this.rial=rial;
        this.referenceId = referenceId;
    }
    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }
    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
    public String getDepositDescription() {
        return depositDescription;
    }

    public void setDepositDescription(String depositDescription) {
        this.depositDescription = depositDescription;
    }

    public String getLoanDescription() {
        return loanDescription;
    }

    public void setLoanDescription(String loanDescription) {
        this.loanDescription = loanDescription;
    }


    public PdfTemplateView() {

    }



    public String getDepositTitles() {
        return depositTitles;
    }

    public void setDepositTitles(String depositTitles) {
        this.depositTitles = depositTitles;
    }

    public String getLoanTitles() {
        return loanTitles;
    }

    public void setLoanTitles(String loanTitles) {
        this.loanTitles = loanTitles;
    }

    public String getSamenTitles() {
        return samenTitles;
    }

    public void setSamenTitles(String samenTitles) {
        this.samenTitles = samenTitles;
    }

    public String getTrackingTitles() {
        return trackingTitles;
    }

    public void setTrackingTitles(String trackingTitles) {
        this.trackingTitles = trackingTitles;
    }

    public String getAmountText() {
        return amountText;
    }

    public void setAmountText(String amountText) {
        this.amountText = amountText;
    }

    public String getTrackingLink() {
        return trackingLink;
    }

    public void setTrackingLink(String trackingLink) {
        this.trackingLink = trackingLink;
    }



}
