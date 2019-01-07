package com.lyonguyen.esky.ws.filters;

import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.factories.ResponseFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Class<?> resourceClass = resourceInfo.getResourceClass();
        Role classRole = extractRole(resourceClass);

        Method resourceMethod = resourceInfo.getResourceMethod();
        Role methodRole = extractRole(resourceMethod);

        Role allowedRole = methodRole != null ? methodRole : classRole;

        if (!checkPermissions(requestContext.getSecurityContext(), allowedRole)) {
            abortWithUnauthorization(requestContext);
        }
    }

    private void abortWithUnauthorization(ContainerRequestContext requestContext) {
        String id = requestContext.getSecurityContext().getUserPrincipal().getName();
        requestContext.abortWith(ResponseFactory.createUnauthorizationResponse(id));
    }

    private Role extractRole(AnnotatedElement annotatedElement) {

        if(annotatedElement == null) {
            return null;
        }

        Secured secured = annotatedElement.getAnnotation(Secured.class);
        if(secured == null) {
            return null;
        }

        return secured.value();
    }

    private boolean checkPermissions(SecurityContext securityContext, Role allowedRole) {

        if(allowedRole == null) {
            return false;
        }
        return securityContext.isUserInRole(allowedRole.toString());
    }
}
