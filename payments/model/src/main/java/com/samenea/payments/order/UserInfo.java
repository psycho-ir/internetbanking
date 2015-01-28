package com.samenea.payments.order;

import com.samenea.commons.component.utils.log.LoggerFactory;
import org.slf4j.Logger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author: Jalal Ashrafi
 * Date: 4/6/13
 */
@Embeddable
public class UserInfo {
    @Column(length = 50,nullable = true)
    private final String firstName;
    @Column(length = 50,nullable = true)
    private final String lastName;

    private UserInfo() {
        lastName = null;
        firstName = null;
    }

    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

