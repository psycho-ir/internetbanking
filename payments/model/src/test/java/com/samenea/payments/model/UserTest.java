package com.samenea.payments.model;

import com.samenea.banking.customer.ICustomer;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/21/13
 * Time: 10:27 AM
 */

public class UserTest {
    private final String username = "username";
    private final String password = "password";
    private ICustomer customer;
    private final String cif = "cif";

    @Before
    public void before() {
        this.customer = new ICustomer() {
            @Override
            public String getName() {
                return "name";
            }

            @Override
            public String getLastName() {
                return "lastName";
            }

            @Override
            public String getCustomerCode() {
                return cif;
            }

            @Override
            public Boolean isUserLock() {
                return false;
            }
        };
    }


    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_exception_when_customer_is_null() {
        User user = new User(null, username, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_exception_when_username_is_null() {
        User user = new User(customer, null, password);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_throw_exception_when_password_is_null() {
        User user = new User(customer, username, null);
    }

    @Test
    public void getCIF_should_return_user_cif() {
        User user = new User(customer, username, password);
        Assert.assertEquals(cif, user.getCIF());
    }

}
