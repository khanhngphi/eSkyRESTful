package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.services.TestService;
import com.lyonguyen.esky.ws.utils.JSONUtils;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestResource {

    @Inject
    private TestService testService;

    @GET
    public Response get() throws Exception{
        String message = testService.test();
        if(true)
            throw new Exception();
        return Response.ok(JSONUtils.asJSON("message", message)).build();
    }
}
