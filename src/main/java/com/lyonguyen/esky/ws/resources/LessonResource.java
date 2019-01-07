package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.services.LessonService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/lessons")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class LessonResource {

    @Inject
    private LessonService lessonService;

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id) {
        return lessonService.get(id);
    }

    @GET
    @Path("{id}/questions")
    public Response getQuestions(@PathParam("id") String id) {
        return lessonService.getQuestions(id);
    }

}
