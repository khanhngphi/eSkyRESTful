package com.lyonguyen.esky.ws.misc;

import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.ws.constants.ApplicationConstant;
import com.lyonguyen.esky.ws.models.UserPrincipal;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class SecurityContextImpl implements SecurityContext {

    private UserPrincipal userPrincipal;

    private SecurityContext securityContext;

    public SecurityContextImpl(SecurityContext securityContext, UserPrincipal userPrincipal) {
        super();
        this.securityContext = securityContext;
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isUserInRole(String s) {
        try {
            Role role = Role.valueOf(s);

            if(userPrincipal.getRole().isGreaterEqual(role)) {
                return true;
            }
            return false;
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isSecure() {
        return securityContext.isSecure();
    }

    @Override
    public String getAuthenticationScheme() {
        return ApplicationConstant.AUTHENTICATION_SCHEME;
    }
}
