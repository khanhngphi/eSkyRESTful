package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.ws.models.request.Credentials;
import com.lyonguyen.esky.ws.services.AuthenticationService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @Inject
    private AuthenticationService authenticationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signIn(final Credentials credentials) {
        return authenticationService.signIn(credentials.getEmail(), credentials.getPassword());
    }

}
