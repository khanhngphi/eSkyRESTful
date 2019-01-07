package com.lyonguyen.esky.ws.models;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lyonguyen.esky.logic.enums.Role;

import java.util.Date;

public class ExtractedJWT {

    private String issuer;

    private String id;

    private Role role;

    private Date expiresAt;

    private String token;

    private ExtractedJWT() {}

    public static ExtractedJWT from(DecodedJWT jwt) {
        ExtractedJWT extractedJWT = new ExtractedJWT();

        extractedJWT.issuer = jwt.getIssuer();
        extractedJWT.id = jwt.getClaim("id").asString();
        String role = jwt.getClaim("role").asString();
        extractedJWT.role = Role.valueOf(role);
        extractedJWT.expiresAt = jwt.getExpiresAt();
        extractedJWT.token = jwt.getToken();

        return extractedJWT;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public String getToken() {
        return token;
    }
}
