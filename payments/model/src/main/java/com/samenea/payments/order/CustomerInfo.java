package com.samenea.payments.order;

import com.samenea.commons.component.model.ValueObject;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author: Jalal Ashrafi
 * Date: 2/2/13
 */
@Embeddable
public class CustomerInfo extends ValueObject {
    @Column(length = 20)
    private final String phoneNumber;
    @Column(length = 50)
    private final String email;

    private CustomerInfo() {
        email = null;
        phoneNumber = null;
    }

    public CustomerInfo(String phoneNumber, String email) {
        //Assert.hasText(phoneNumber,"phone number can not be null or empty");
        //Assert.hasText(email,"email could not be null or empty");
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerInfo that = (CustomerInfo) o;

        if (!email.equals(that.email)) return false;
        if (!phoneNumber.equals(that.phoneNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phoneNumber.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

