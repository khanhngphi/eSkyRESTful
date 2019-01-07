package com.lyonguyen.esky.ws.filters;

import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.AccountDAO;
import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.constants.ApplicationConstant;
import com.lyonguyen.esky.ws.factories.ResponseFactory;
import com.lyonguyen.esky.ws.misc.SecurityContextImpl;
import com.lyonguyen.esky.ws.models.ExtractedJWT;
import com.lyonguyen.esky.ws.models.UserPrincipal;
import com.lyonguyen.esky.ws.utils.JWTUtils;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private AccountDAO accountDAO = DatabaseContext.getInstance().getAccountDAO();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthenticationRequest(requestContext);
            return;
        }

        String token = authorizationHeader.substring(ApplicationConstant.AUTHENTICATION_SCHEME.length()).trim();

        ExtractedJWT jwt = decodeToken(token);

        if(jwt == null) {
            abortWithIllegalToken(requestContext);
            return;
        }

        if(verifyToken(jwt) == null) {
            abortWithUnauthenticationToken(requestContext);
            return;
        }

        setSecurityContext(requestContext, jwt);
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.toLowerCase().startsWith(ApplicationConstant.AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthenticationRequest(ContainerRequestContext requestContext) {
        requestContext.abortWith(ResponseFactory.createUnauthenticationRequestResponse());
    }

    private void abortWithIllegalToken(ContainerRequestContext requestContext) {
        requestContext.abortWith(ResponseFactory.createIllegalTokenResponse());
    }

    private void abortWithUnauthenticationToken(ContainerRequestContext requestContext) {
        requestContext.abortWith(ResponseFactory.createUnauthenticationTokenResponse());
    }

    private ExtractedJWT decodeToken(String token) {
        return JWTUtils.decode(token);
    }

    private ExtractedJWT verifyToken(ExtractedJWT jwt) {
        if(!JWTUtils.checkExpires(jwt)) {
            return null;
        }
        String id = jwt.getId();

        String key = accountDAO.getSecretKey(id);

        return JWTUtils.verify(key, jwt.getToken());
    }

    private void setSecurityContext(ContainerRequestContext requestContext, ExtractedJWT jwt) {
        UserPrincipal userPrincipal = new UserPrincipal(jwt.getId(), jwt.getRole());

        SecurityContext securityContext = requestContext.getSecurityContext();

        SecurityContextImpl securityContextImpl = new SecurityContextImpl(securityContext, userPrincipal);

        requestContext.setSecurityContext(securityContextImpl);
    }
}
