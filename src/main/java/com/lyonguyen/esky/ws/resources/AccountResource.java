package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.models.request.AccountUpdateInfo;
import com.lyonguyen.esky.ws.models.request.SignUpInfo;
import com.lyonguyen.esky.ws.services.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    private AccountService accountService;

    @HEAD
    @Path("{emailOrUsername}")
    public Response hasEmail(@PathParam("emailOrUsername") String emailOrUsername) {
        return accountService.hasEmailOrUsername(emailOrUsername);
    }

    @GET
    @Secured
    public Response getById(@Context SecurityContext securityContext) {
        String id = securityContext.getUserPrincipal().getName();
        return accountService.getById(id);
    }

    @GET
    @Path("{username}")
    @Secured
    public Response getByUsername(@PathParam("username") String username) {
        return accountService.getByUsername(username);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUp(final SignUpInfo info) {
        return accountService.signUp(info.getEmail(), info.getName(), info.getPassword());
    }

    @GET
    @Path("verify/{token}")
    public Response verifyEmail(@PathParam("token") String token) {
        return accountService.verifyEmail(token);
    }

    @GET
    @Path("{id}/verify")
    public Response resendVerifyEmail(@PathParam("id") String id) {
        return accountService.resendVerifyEmail(id);
    }

    @PUT
    @Path("username")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUsername(@Context SecurityContext securityContext,
                                  AccountUpdateInfo info)
    {
        String id = securityContext.getUserPrincipal().getName();
        return accountService.updateUsername(id, info.getUsername(), info.getCredentials());
    }

    @PUT
    @Path("name")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateName(@Context SecurityContext securityContext,
                                   AccountUpdateInfo info)
    {
        String id = securityContext.getUserPrincipal().getName();
        return accountService.updateName(id, info.getName(), info.getCredentials());
    }

    @PUT
    @Path("password")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(@Context SecurityContext securityContext,
                                   AccountUpdateInfo info)
    {
        String id = securityContext.getUserPrincipal().getName();
        return accountService.updateUsername(id, info.getPassword(), info.getCredentials());
    }

}
