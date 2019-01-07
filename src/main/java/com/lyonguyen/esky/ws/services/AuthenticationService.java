package com.lyonguyen.esky.ws.services;

import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.AccountDAO;
import com.lyonguyen.esky.logic.models.Account;
import com.lyonguyen.esky.ws.factories.ResponseFactory;
import com.lyonguyen.esky.ws.utils.EmailVerifier;
import com.lyonguyen.esky.ws.utils.JWTUtils;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

@Resource
public class AuthenticationService {

    private AccountDAO accountDAO = DatabaseContext.getInstance().getAccountDAO();

    public Response signIn(String email, String password) {
        String id = accountDAO.signIn(email, password);

        if(id == null) {
            return ResponseFactory.createSignInFailedResponse();
        }

        String key = accountDAO.getSecretKey(id);

        Account user = accountDAO.getById(id);

        if(!accountDAO.checkEmailVerified(id)) {
            EmailVerifier.sendVerifyEmail(id, email, key);
            return ResponseFactory.createResponseWithEntity(user);
        }

        String jwt = JWTUtils.sign(key, id, user.getRole());

        user.setToken(jwt);

        return ResponseFactory.createResponseWithEntity(user);
    }
}
