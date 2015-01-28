package com.samenea.payments.model;

import com.samenea.payments.security.BankingAuthenticationToken;
import com.samenea.payments.security.SpringSecurityBasedUserContext;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

/**
 * @author: Jalal Ashrafi
 * Date: 1/22/13
 */
public class SpringSecurityBasedUserContextTest {

    private String examplePassword;
    private String exampleUserName;

    @Before
    public void setUp() throws Exception {
        examplePassword = "examplePassword";
        exampleUserName = "exampleUserName";
        SecurityContextHolder.getContext().setAuthentication(new BankingAuthenticationToken(exampleUserName,examplePassword,"alaki"));
    }


    @Test
    public void currentUser_should_return_logged_in_userName(){
        SpringSecurityBasedUserContext springSecurityBasedUserContext = new SpringSecurityBasedUserContext();
        final Principal principal = springSecurityBasedUserContext.currentUser();
        Assert.assertEquals(exampleUserName,principal.getName());
    }
}
