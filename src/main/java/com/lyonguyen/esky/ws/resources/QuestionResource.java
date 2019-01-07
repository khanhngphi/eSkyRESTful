package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.services.QuestionService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/questions")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class QuestionResource {

    @Inject
    private QuestionService questionService;

    @GET
    public Response getAll() {
        return questionService.getAll();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id) {
        return questionService.get(id);
    }

}
