package com.samenea.payments.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * An {@link org.springframework.security.core.Authentication} implementation that is designed for simple presentation
 * of a username and password and CIF of bank customer.
 * <p/>
 * The <code>principal</code> and <code>credentials</code> and <code>cif</code> should be set with an <code>Object</code> that provides
 * the respective property via its <code>Object.toString()</code> method. The simplest such <code>Object</code> to use
 * is <code>String</code>.
 *
 * @author Soroosh Sarabadani
 */

public class BankingAuthenticationToken extends UsernamePasswordAuthenticationToken {

    //~ Instance fields ================================================================================================
    private final String cif;

    //~ Constructors ===================================================================================================

    /**
     * This constructor can be safely used by any code that wishes to create a
     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link
     * #isAuthenticated()} will return <code>false</code>.
     */
    public BankingAuthenticationToken(Object principal, Object credentials, String cif) {
        super(principal, credentials);
        this.cif = cif;
        setAuthenticated(false);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or <code>AuthenticationProvider</code>
     * implementations that are satisfied with producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     *
     * @param principal
     * @param credentials
     * @param authorities
     */
    public BankingAuthenticationToken(Object principal, Object credentials, String cif, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.cif = cif;
        super.setAuthenticated(true); // must use super, as we override
    }


    //~ Methods ========================================================================================================


    public String getCIF() {
        return this.cif;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

}
