package com.samenea.payments.security;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.customer.ICustomerService;
import com.samenea.commons.component.model.exceptions.NotFoundException;
import com.samenea.payments.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/20/13
 * Time: 2:15 PM
 */
@Service("bankingAuthenticationProvider")
public class BankingAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private ICustomerService customerService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final BankingAuthenticationToken bankingAuthenticationToken = (BankingAuthenticationToken) authentication;
        final String password = bankingAuthenticationToken.getCredentials().toString();
        final String cif = bankingAuthenticationToken.getCIF();
        final String finalUsername = username;

        try {
            final ICustomer customer = customerService.findCustomer(cif, username, password);

            return new User(customer, username, password);        } catch (NotFoundException e) {
            throw new AuthenticationCredentialsNotFoundException("Customer not found.", e);
        }
    }
}
