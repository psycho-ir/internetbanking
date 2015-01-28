package com.samenea.payments.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/20/13
 * Time: 1:35 PM
 */

public class BankingAuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {
    public static final String SPRING_SECURITY_FORM_CIF_KEY = "j_cif";

    protected BankingAuthenticationProcessingFilter() {
        super();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String cif = obtainCIF(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if (cif == null) {
            cif = "";
        }

        username = username.trim();

        BankingAuthenticationToken token = new BankingAuthenticationToken(username, password, cif);

        // Allow subclasses to set the "details" property
        setDetails(request, token);

        return this.getAuthenticationManager().authenticate(token);
    }

    private String obtainCIF(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_CIF_KEY);
    }

}
