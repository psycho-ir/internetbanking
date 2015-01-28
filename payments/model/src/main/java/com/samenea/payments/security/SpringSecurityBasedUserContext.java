package com.samenea.payments.security;

import com.samenea.commons.component.utils.UserContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

/**
 * @author: Jalal Ashrafi
 * Date: 1/22/13
 */
//todo should be moved to a common component
@Component
public class SpringSecurityBasedUserContext implements UserContext {

    @Override
    public Principal currentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
