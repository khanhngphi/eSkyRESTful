package com.lyonguyen.esky.ws.factories;

import com.lyonguyen.esky.ws.utils.JSONUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResponseFactory {

    public static Response createOkResponse() {
        return Response.ok().build();
    }

    public static Response createResponseWithEntity(Object entity) {
        return Response.ok(entity, MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createResponseWithEntity(String name, String value) {
        return Response.ok(JSONUtils.asJSON(name, value), MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createResponseWithEntity(String name, int value) {
        return Response.ok(JSONUtils.asJSON(name, value), MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createResponseWithEntity(String name, boolean value) {
        return Response.ok(JSONUtils.asJSON(name, value), MediaType.APPLICATION_JSON)
                .build();
    }

    /** Error Response **/

    public static Response createInternalServerErrorResponse() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorMessageFactory.createInternalServerErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createSignInFailedResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessageFactory.createSignInFailedErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createUnauthenticationRequestResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessageFactory.createUnauthenticationRequestErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createIllegalTokenResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessageFactory.createIllegalTokenErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createUnauthenticationTokenResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessageFactory.createUnauthenticationTokenErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createExpiredTokenResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(ErrorMessageFactory.createExpiredTokenErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createUnauthorizationResponse(String id) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(ErrorMessageFactory.createForbiddenErrorMessage(id))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createWrongPasswordErrorMessage() {
        return Response.status(Response.Status.NOT_ACCEPTABLE)
                .entity(ErrorMessageFactory.createWrongPasswordErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createEmailNotFoundResponse(String email) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorMessageFactory.createEmailNotFoundErrorMessage(email))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createUserNotFoundResponse(String id) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorMessageFactory.createUserNotFoundErrorMessage(id))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createContentNotFoundResponse(String id) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorMessageFactory.createContentNotFoundErrorMessage(id))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createSignUpInfoInvalidResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorMessageFactory.createSignUpInfoInvalidErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createSignUpConflictResponse(String email) {
        return Response.status(Response.Status.CONFLICT)
                .entity(ErrorMessageFactory.createSignUpConflictErrorMessage(email))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createEmailVerifiedResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorMessageFactory.createEmailVerifiedErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createEmailVerifyTokenInvalidResponse() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorMessageFactory.createEmailVerifyTokenInvalidErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public static Response createUpdateInfoInvalidErrorMessage() {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorMessageFactory.createUpdateInfoInvalidErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }


    public static Response createUpdateConflictErrorMessage() {
        return Response.status(Response.Status.CONFLICT)
                .entity(ErrorMessageFactory.createUpdateConflictErrorMessage())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }




}
