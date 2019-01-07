package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.models.request.LessonProgress;
import com.lyonguyen.esky.ws.services.LearnerService;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path("/learners")
@Secured
@Produces(MediaType.APPLICATION_JSON)
public class LearnerResource {

    @Inject
    private LearnerService learnerService;

    @GET
    @Path("{id}")
    @Secured(Role.MANAGER)
    public Response getLearner(@PathParam("id") String id) {
        return learnerService.get(id);
    }

    @GET
    public Response get(@Context SecurityContext securityContext) {
        String id = securityContext.getUserPrincipal().getName();
        return learnerService.get(id);
    }

    @GET
    @Path("{id}/lessons")
    @Secured(Role.MANAGER)
    public Response getLearnerLessons(@PathParam("id") String id) {
        return learnerService.getLessons(id);
    }

    @GET
    @Path("lessons")
    public Response getLessons(@Context SecurityContext securityContext) {
        String id = securityContext.getUserPrincipal().getName();
        return learnerService.getLessons(id);
    }

    @GET
    @Path("lessons/{lessonId}")
    public Response getLesson(@Context SecurityContext securityContext, @PathParam("lessonId") String lessonId) {
        String id = securityContext.getUserPrincipal().getName();
        return learnerService.getLesson(id, lessonId);
    }


    @PUT
    @Path("lessons/{lessonId}/questions/{questionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response answerQuestion(@Context SecurityContext securityContext,
                                   @PathParam("lessonId") String lessonId,
                                   @PathParam("questionId") String questionId,
                                   String answer)
    {
        String id = securityContext.getUserPrincipal().getName();
        return learnerService.answerQuestion(id, lessonId, questionId, answer);
    }

    @GET
    @Path("lessons/{lessonId}/reward")
    public Response getLessonReward(@Context SecurityContext securityContext,
                                    @PathParam("lessonId") String lessonId)
    {
        String id = securityContext.getUserPrincipal().getName();
        return learnerService.getLessonReward(id, lessonId);
    }

    @GET
    @Path("levelinfo")
    public Response getLevelInfo() {
        return learnerService.getLevelInfo();
    }

}
