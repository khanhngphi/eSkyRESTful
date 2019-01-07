package com.lyonguyen.esky.ws.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.ws.models.ExtractedJWT;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Date;

public class JWTUtils {

    private static final String ISSUER = "com/lyonguyen/esky";

    private static final Integer USER_DAY_EXPIRES = 3;

    private static final Integer ADMIN_DAY_EXPIRES = 1;

    public static String signForUser(String key, String id) {
        return sign(key, id, Role.LEARNER, DateUtils.asDate(LocalDate.now().plusDays(USER_DAY_EXPIRES)));
    }

    public static String signForContributor(String key, String id) {
        return sign(key, id, Role.CONTRIBUTOR, DateUtils.asDate(LocalDate.now().plusDays(USER_DAY_EXPIRES)));
    }

    public static String signForAdmin(String key, String id) {

        return sign(key, id, Role.ADMIN, DateUtils.asDate(LocalDate.now().plusDays(ADMIN_DAY_EXPIRES)));
    }

    public static String sign(String key, String id, Role role, Date expiresAt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("id", id)
                    .withClaim("role", role.toString())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        } catch (JWTCreationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String sign(String key, String id, Role role) {
        switch (role) {
            case ADMIN:
                /*jwt = JWTUtils.signForAdmin(key, id);
                user.setToken(jwt);
                return Response.ok(JSONUtils.asJSON(user)).build();*/
            case MANAGER:
                return signForAdmin(key, id);
            case CONTRIBUTOR:
                return signForContributor(key, id);
            case LEARNER:
                return signForUser(key, id);
            default:
                return null;
        }
    }

    public static ExtractedJWT verify(String key, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return ExtractedJWT.from(jwt);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        } catch (JWTVerificationException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean checkExpires(ExtractedJWT jwt) {
        if(jwt.getExpiresAt() == null) {
            return true;
        }
        return jwt.getExpiresAt().after(new Date());
    }

    public static ExtractedJWT decode(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return ExtractedJWT.from(jwt);
        } catch (JWTDecodeException ex) {
            return null;
        }
    }
}
