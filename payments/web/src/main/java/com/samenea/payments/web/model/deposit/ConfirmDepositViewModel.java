package com.samenea.payments.web.model.deposit;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.customer.ICustomerService;
import com.samenea.banking.simia.model.Customer;
import com.samenea.commons.component.utils.persian.NumberToWritten;
import com.samenea.payments.model.DepositParams;
import com.samenea.payments.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 1/27/13
 * Time: 12:13 PM
 *
 * @Author:payam
 */
@Configuration
public class ConfirmDepositViewModel {
    private String amountDepositNumeric;
    private String numberDeposit;
    private String amountDepositLetter;
    private List<String> accountsHolderName;
    private String description;
    private String phoneNumber;
    private String email;
    private int amount;
    @Autowired
    ICustomerService customerService;

    public ConfirmDepositViewModel(Order order) {
        if (order != null) {
            DepositParams depositParams = DepositParams.fromCriteriaString(order.getLineItems().get(0).getProductSpec().getCriteria());
            this.amount = order.getTotalPrice();
            this.amountDepositLetter = NumberToWritten.convert(Integer.parseInt(amountDepositNumeric));
            this.amount = Integer.parseInt(amountDepositNumeric);
            accountsHolderName = new ArrayList<String>();
            this.phoneNumber = order.getCustomerInfo().getPhoneNumber();
            this.email = order.getCustomerInfo().getEmail();
            accountsHolderName = new ArrayList<String>();
            for (ICustomer customer : customerService.findCustomersOfDeposit(depositParams.getDepositNumber())) {
                accountsHolderName.add(customer.getName() + " " + customer.getLastName());
            }
        }
    }

    public ConfirmDepositViewModel(ChargeDepositViewModel chargeDepositViewModel, List<ICustomer> customers) {
        this.amountDepositNumeric = chargeDepositViewModel.getAmount();
        this.numberDeposit = chargeDepositViewModel.getDepositNumber1() + "." + chargeDepositViewModel.getDepositNumber2();
        this.description = chargeDepositViewModel.getDescription();
        this.phoneNumber = chargeDepositViewModel.getPhoneNumber();
        this.email = chargeDepositViewModel.getEmail();


        int amount = Integer.parseInt(amountDepositNumeric);
        if (amount > 1000000000) {
            this.amountDepositLetter = "";
        } else {
            this.amountDepositLetter = NumberToWritten.convert(amount);
        }
        this.amount = amount;
        accountsHolderName = new ArrayList<String>();
        for (ICustomer customer : customers) {
            accountsHolderName.add(customer.getName() + " " + customer.getLastName());
        }
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ConfirmDepositViewModel() {

    }

    public String getNumberDeposit() {
        return numberDeposit;
    }

    public void setNumberDeposit(String numberDeposit) {
        this.numberDeposit = numberDeposit;
    }

    public String getAmountDepositNumeric() {
        return amountDepositNumeric;
    }

    public void setAmountDepositNumeric(String amountDepositNumeric) {
        this.amountDepositNumeric = amountDepositNumeric;
    }

    public String getAmountDepositLetter() {
        return amountDepositLetter;
    }

    public void setAmountDepositLetter(String amountDepositLetter) {
        this.amountDepositLetter = amountDepositLetter;
    }

    public List<String> getAccountsHolderName() {
        return accountsHolderName;
    }

    public void setAccountsHolderName(List<String> accountsHolderName) {
        this.accountsHolderName = accountsHolderName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ConfirmDepositViewModel{" +
                "Amount='" + amountDepositNumeric + '\'' +
                ",DepositNumber='" + numberDeposit + '\'' +
                ", PhoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
