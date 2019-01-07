package com.lyonguyen.esky.ws.providers;

import com.lyonguyen.esky.ws.factories.ResponseFactory;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorHandler implements ExceptionMapper<InternalServerErrorException> {

    @Override
    public Response toResponse(InternalServerErrorException e) {
        return ResponseFactory.createInternalServerErrorResponse();
    }
}
