package com.lyonguyen.esky.ws.resources;

import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.ws.annotations.Secured;
import com.lyonguyen.esky.ws.models.request.LessonContribute;
import com.lyonguyen.esky.ws.models.request.QuestionContribute;
import com.lyonguyen.esky.ws.services.ContributorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path("/contributors")
@Secured(Role.CONTRIBUTOR)
@Produces(MediaType.APPLICATION_JSON)
public class ContributorResource {

    @Inject
    private ContributorService contributorService;

    @POST
    @Path("lesson")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertLesson(@Context SecurityContext securityContext,
                                   LessonContribute info) {
        String id = securityContext.getUserPrincipal().getName();
        boolean accepted = securityContext.isUserInRole(Role.MANAGER.name());
        return contributorService.insertLesson(id, accepted, info);
    }

    @PUT
    @Path("lesson/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateLesson(@Context SecurityContext securityContext,
                                   @PathParam("id") String lessonId,
                                   LessonContribute info) {
        String id = securityContext.getUserPrincipal().getName();
        boolean accepted = securityContext.isUserInRole(Role.MANAGER.name());
        return contributorService.updateLesson(id, lessonId, accepted, info);
    }

    @DELETE
    @Path("lesson/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteLesson(@Context SecurityContext securityContext,
                                   @PathParam("id") String lessonId,
                                   @DefaultValue("") @QueryParam("note") String note) {
        String id = securityContext.getUserPrincipal().getName();
        boolean accepted = securityContext.isUserInRole(Role.MANAGER.name());
        return contributorService.deleteLesson(id, lessonId, accepted, note);
    }

    @POST
    @Path("question")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertQuestion(@Context SecurityContext securityContext,
                                   QuestionContribute info) {
        String id = securityContext.getUserPrincipal().getName();
        boolean accepted = securityContext.isUserInRole(Role.MANAGER.name());
        return contributorService.insertQuestion(id, accepted, info);
    }

    @PUT
    @Path("question/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateQuestion(@Context SecurityContext securityContext,
                                   @PathParam("id") String questionId,
                                   QuestionContribute info) {
        String id = securityContext.getUserPrincipal().getName();
        boolean accepted = securityContext.isUserInRole(Role.MANAGER.name());
        return contributorService.updateQuestion(id, questionId, accepted, info);
    }

    @DELETE
    @Path("question/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(@Context SecurityContext securityContext,
                                   @PathParam("id") String questionId,
                                   @DefaultValue("") @QueryParam("note") String note) {
        String id = securityContext.getUserPrincipal().getName();
        boolean accepted = securityContext.isUserInRole(Role.MANAGER.name());
        return contributorService.deleteQuestion(id, questionId, accepted, note);
    }

}
