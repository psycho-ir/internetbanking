package com.samenea.payments.web.model.deposit;

import com.samenea.captcha.CaptchaText;
import com.samenea.commons.component.utils.persian.NumberUtil;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * Date: 1/23/13
 * Time: 4:56 PM
 *
 * @Author:payam
 */
public class ChargeDepositViewModel {

    @NotEmpty
    private String depositNumber1;
    @NotEmpty
    private String depositNumber2;
    @NotEmpty
    private String description;
    @CaptchaText
    private String jCaptchaResponse;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String amount;

    public String getPhoneNumber() {
        return NumberUtil.convertNumbersToAscii(phoneNumber);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NotEmpty
    private String phoneNumber;

    public ChargeDepositViewModel() {
    }

    public ChargeDepositViewModel(String depositNumber1, String depositNumber2, String description, String email, String phoneNumber, String amount) {
        this.depositNumber1 = depositNumber1;
        this.depositNumber1 = depositNumber2;
        this.description = description;
        this.email = email;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public String getDepositNumber1() {
        return NumberUtil.convertNumbersToAscii(depositNumber1);
    }

    public void setDepositNumber1(String depositNumber) {
        this.depositNumber1 = depositNumber;
    }

    public String getDepositNumber2() {
        return NumberUtil.convertNumbersToAscii(depositNumber2);
    }

    public void setDepositNumber2(String depositNumber) {
        this.depositNumber2 = depositNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmount() {
        if (amount == null) {
            return null;
        }
        return NumberUtil.convertNumbersToAscii(amount).replaceAll(",", "");
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getjCaptchaResponse() {
        return jCaptchaResponse;
    }

    public void setjCaptchaResponse(String jCaptchaResponse) {
        this.jCaptchaResponse = jCaptchaResponse;
    }
}
