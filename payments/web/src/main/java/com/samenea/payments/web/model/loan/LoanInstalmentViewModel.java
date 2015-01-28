package com.samenea.payments.web.model.loan;

import com.samenea.captcha.CaptchaText;
import com.samenea.commons.component.utils.persian.NumberUtil;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Date: 2/12/13
 * Time: 10:29 AM
 *
 * @Author:payam
 */
public class LoanInstalmentViewModel {
    @CaptchaText
    private  String jCaptchaResponse;
    @NotEmpty
    @LoanNumber
    private String number;
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String phoneNumber;
    public LoanInstalmentViewModel(String number, String email,String phoneNumber) {
        this.number = number;
        this.email = email;
        this.phoneNumber=phoneNumber;
    }
    public String getPhoneNumber() {
        return NumberUtil.convertNumbersToAscii(phoneNumber);
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public LoanInstalmentViewModel() {

    }
    public String getNumber() {
        return NumberUtil.convertNumbersToAscii(number);
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getjCaptchaResponse() {
        return jCaptchaResponse;
    }

    public void setjCaptchaResponse(String jCaptchaResponse) {
        this.jCaptchaResponse = jCaptchaResponse;
    }
}
