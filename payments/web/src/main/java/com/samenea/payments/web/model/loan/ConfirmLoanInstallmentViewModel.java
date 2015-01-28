package com.samenea.payments.web.model.loan;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.loan.IInstallment;
import com.samenea.banking.loan.ILoan;
import com.samenea.banking.loan.ILoanService;
import com.samenea.banking.loan.InstallmentStatus;
import com.samenea.commons.component.utils.persian.NumberToWritten;

/**
 * Date: 2/12/13
 * Time: 2:52 PM
 *
 * @Author:payam
 */
public class ConfirmLoanInstallmentViewModel {
    private String email;
    private String loanNumber;
    private String loanAmount;
    private String orginalAmount;
    private String penaltyAmount;
    private String installmentspayableNumber;
    private String installmentspayableAmount;
    private String installmentspayableAmountLetter;
    private String typeOfLoan;
    private String loanAmountLetter;
    private String orginalAmountLetter;
    private String penaltyAmountLetter;
    private String name;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private String phoneNumber;

    public ConfirmLoanInstallmentViewModel(String loanNumber, ILoanService loanService, String email, String phoneNumber) {
        ILoan loan = loanService.findLoan(loanNumber);
        ICustomer customer = new ICustomer() {
            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getLastName() {
                return "";
            }

            @Override
            public String getCustomerCode() {
                return "";
            }

            @Override
            public Boolean isUserLock() {
                return false;
            }
        };
        try {
            customer = loanService.findCustomer(loanNumber);
        } catch (Exception e) {

        }
        this.name = customer.getName();
        this.lastName = customer.getLastName();
        this.loanNumber = loan.getLoanNumber();
        this.loanAmount = loan.getOriginalAmount().toString();
        if (loan.getOriginalAmount().intValue() > 1000000000) {
            this.loanAmountLetter = "";
        } else {
            this.loanAmountLetter = NumberToWritten.convert(loan.getOriginalAmount().intValue());
        }
        this.email = email;
        this.phoneNumber = phoneNumber;
        IInstallment payInstallment;
        if (loan.hasPayableInstallment()) {
            payInstallment = loan.getPayableInstallment();
            if (payInstallment.getPayableAmount().intValue() > 1000000000) {
                this.installmentspayableAmountLetter = "";
            } else {
                this.installmentspayableAmountLetter = NumberToWritten.convert(payInstallment.getPayableAmount().intValue());
            }
            this.installmentspayableNumber = payInstallment.getInstallmentNumber();
            this.installmentspayableAmount = payInstallment.getPayableAmount().toString();
            this.penaltyAmount = payInstallment.getPenaltyAmount().toString();
            this.orginalAmount = payInstallment.getUnPayedAmount().toString();
            if (payInstallment.getPenaltyAmount().intValue() > 1000000000) {
                this.penaltyAmountLetter = "";
            } else {
                this.penaltyAmountLetter = NumberToWritten.convert(payInstallment.getPenaltyAmount().intValue());
            }
            if (payInstallment.getUnPayedAmount().intValue() > 1000000000) {
                this.orginalAmountLetter = "";
            } else {
                this.orginalAmountLetter = NumberToWritten.convert(payInstallment.getUnPayedAmount().intValue());
            }

        }
        this.typeOfLoan = loan.getLoanDescription();
    }

    public String getInstallmentspayableAmountLetter() {
        return installmentspayableAmountLetter;
    }

    public void setInstallmentspayableAmountLetter(String installmentspayableAmountLetter) {
        this.installmentspayableAmountLetter = installmentspayableAmountLetter;
    }

    public String getOrginalAmountLetter() {
        return orginalAmountLetter;
    }

    public void setOrginalAmountLetter(String orginalAmountLetter) {
        this.orginalAmountLetter = orginalAmountLetter;
    }

    public String getPenaltyAmountLetter() {
        return penaltyAmountLetter;
    }

    public void setPenaltyAmountLetter(String penaltyAmountLetter) {
        this.penaltyAmountLetter = penaltyAmountLetter;
    }

    public String getOrginalAmount() {
        return orginalAmount;
    }

    public String getLoanAmountLetter() {
        return loanAmountLetter;
    }

    public void setLoanAmountLetter(String loanAmountLetter) {
        this.loanAmountLetter = loanAmountLetter;
    }

    public void setOrginalAmount(String orginalAmount) {
        this.orginalAmount = orginalAmount;
    }

    public ConfirmLoanInstallmentViewModel() {

    }

    public String getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(String penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstallmentspayableAmount() {
        return installmentspayableAmount;
    }

    public void setInstallmentspayableAmount(String installmentspayableAmount) {
        this.installmentspayableAmount = installmentspayableAmount;
    }

    public String getInstallmentspayableNumber() {
        return installmentspayableNumber;
    }

    public void setInstallmentspayableNumber(String installmentspayableNumber) {
        this.installmentspayableNumber = installmentspayableNumber;
    }

    public String getTypeOfLoan() {
        return typeOfLoan;
    }

    public void setTypeOfLoan(String typeOfLoan) {
        this.typeOfLoan = typeOfLoan;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    @Override
    public String toString() {
        return "ConfirmLoanInstallmentViewModel{" +
                "email='" + email + '\'' +
                ", loanNumber='" + loanNumber + '\'' +
                ", loanAmount='" + loanAmount + '\'' +
                ", orginalAmount='" + orginalAmount + '\'' +
                ", penaltyAmount='" + penaltyAmount + '\'' +
                ", installmentspayableNumber='" + installmentspayableNumber + '\'' +
                ", installmentspayableAmount='" + installmentspayableAmount + '\'' +
                ", installmentspayableAmountLetter='" + installmentspayableAmountLetter + '\'' +
                ", typeOfLoan='" + typeOfLoan + '\'' +
                ", loanAmountLetter='" + loanAmountLetter + '\'' +
                ", orginalAmountLetter='" + orginalAmountLetter + '\'' +
                ", penaltyAmountLetter='" + penaltyAmountLetter + '\'' +
                '}';
    }
}
