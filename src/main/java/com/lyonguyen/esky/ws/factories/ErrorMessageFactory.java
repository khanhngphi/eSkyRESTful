package com.lyonguyen.esky.ws.factories;

import com.lyonguyen.esky.ws.models.response.ErrorMessage;

import javax.ws.rs.core.Response;

public class ErrorMessageFactory {

    public static ErrorMessage createInternalServerErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        errorMessage.setCode(0);
        errorMessage.setMessage("Unexpected error. Please try again");
        return errorMessage;
    }

    public static ErrorMessage createSignInFailedErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.UNAUTHORIZED.getStatusCode());
        errorMessage.setCode(10);
        errorMessage.setMessage("Email or password invalid");
        return errorMessage;
    }

    public static ErrorMessage createUnauthenticationRequestErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.UNAUTHORIZED.getStatusCode());
        errorMessage.setCode(11);
        errorMessage.setMessage("Unauthentication request");
        return errorMessage;
    }

    public static ErrorMessage createIllegalTokenErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.UNAUTHORIZED.getStatusCode());
        errorMessage.setCode(12);
        errorMessage.setMessage("Illegal token format");
        return errorMessage;
    }

    public static ErrorMessage createUnauthenticationTokenErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.UNAUTHORIZED.getStatusCode());
        errorMessage.setCode(13);
        errorMessage.setMessage("Unauthentication token");
        return errorMessage;
    }

    public static ErrorMessage createExpiredTokenErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.UNAUTHORIZED.getStatusCode());
        errorMessage.setCode(14);
        errorMessage.setMessage("Token has expired");
        return errorMessage;
    }

    public static ErrorMessage createForbiddenErrorMessage(String id) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.FORBIDDEN.getStatusCode());
        errorMessage.setCode(15);
        errorMessage.setMessage(String.format("Unauthorization user#%s", id));
        return errorMessage;
    }

    public static ErrorMessage createWrongPasswordErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.NOT_ACCEPTABLE.getStatusCode());
        errorMessage.setCode(16);
        errorMessage.setMessage("Wrong password");
        return errorMessage;
    }

    public static ErrorMessage createEmailNotFoundErrorMessage(String email) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
        errorMessage.setCode(20);
        errorMessage.setMessage(String.format("Can't not found email \"%s\"", email));
        return errorMessage;
    }

    public static ErrorMessage createUserNotFoundErrorMessage(String id) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
        errorMessage.setCode(21);
        errorMessage.setMessage(String.format("Can't not found user#%s", id));
        return errorMessage;
    }

    public static ErrorMessage createContentNotFoundErrorMessage(String id) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.NOT_FOUND.getStatusCode());
        errorMessage.setCode(22);
        errorMessage.setMessage(String.format("Cant't not found content#%s", id));
        return errorMessage;
    }

    public static ErrorMessage createSignUpInfoInvalidErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        errorMessage.setCode(30);
        errorMessage.setMessage("Sign up information invalid");
        return errorMessage;
    }


    public static ErrorMessage createSignUpConflictErrorMessage(String email) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.CONFLICT.getStatusCode());
        errorMessage.setCode(31);
        errorMessage.setMessage(String.format("Email \"%s\" already exists", email));
        return errorMessage;
    }

    public static ErrorMessage createEmailVerifiedErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        errorMessage.setCode(32);
        errorMessage.setMessage("Email has been verified");
        return errorMessage;
    }

    public static ErrorMessage createEmailVerifyTokenInvalidErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        errorMessage.setCode(33);
        errorMessage.setMessage("Token has expired or invalid");
        return errorMessage;
    }

    public static ErrorMessage createUpdateInfoInvalidErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
        errorMessage.setCode(34);
        errorMessage.setMessage("Update information invalid");
        return errorMessage;
    }


    public static ErrorMessage createUpdateConflictErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatusCode(Response.Status.CONFLICT.getStatusCode());
        errorMessage.setCode(35);
        errorMessage.setMessage("Update information Conflict");
        return errorMessage;
    }


}
