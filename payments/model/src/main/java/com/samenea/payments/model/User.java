package com.samenea.payments.model;

import com.samenea.banking.customer.ICustomer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * A wrapper for UserDetails with necessary properties for payments system.
 * @author: Soroosh Sarabadani
 * Date: 1/21/13
 * Time: 9:59 AM
 */

public class User implements UserDetails {

    private final ICustomer customer;
    private final String username;
    private final String password;


    public User(ICustomer customer, String username, String password) {
        Assert.notNull(customer,"customer cannot be null.");
        Assert.notNull(username,"username cannot be null.");
        Assert.notNull(password,"password cannot be null.");
        this.customer = customer;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.customer.isUserLock();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @return the Fist name of customer
     */
    public String getName() {
        return this.customer.getName();
    }

    /**
     * @return the Last name of customer
     */

    public String getLastName() {
        return this.customer.getLastName();
    }

    /**
     * @return the customer code of customer
     */
    public String getCIF() {
        return this.customer.getCustomerCode();
    }

}
