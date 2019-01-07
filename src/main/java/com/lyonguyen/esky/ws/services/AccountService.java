package com.lyonguyen.esky.ws.services;

import com.lyonguyen.esky.logic.dao.DatabaseContext;
import com.lyonguyen.esky.logic.dao.AccountDAO;
import com.lyonguyen.esky.logic.enums.Role;
import com.lyonguyen.esky.logic.models.Account;
import com.lyonguyen.esky.ws.factories.ResponseFactory;
import com.lyonguyen.esky.ws.utils.EmailVerifier;
import com.lyonguyen.esky.ws.utils.JWTUtils;
import com.lyonguyen.esky.ws.utils.Validator;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

@Resource
public class AccountService {

    private AccountDAO accountDAO = DatabaseContext.getInstance().getAccountDAO();

    public Response hasEmailOrUsername(String emailOrUsername) {
        if(Validator.email(emailOrUsername)) {
            return hasEmail(emailOrUsername);
        }
        return hasUsername(emailOrUsername);
    }

    public Response hasEmail(String email) {
        if (accountDAO.hasEmail(email)) {
            return ResponseFactory.createOkResponse();
        } else {
            return ResponseFactory.createEmailNotFoundResponse(email);
        }
    }

    public Response hasUsername(String username) {
        if(accountDAO.hasUsername(username)) {
            return ResponseFactory.createOkResponse();
        } else {
            return ResponseFactory.createUserNotFoundResponse(username);
        }
    }

    public Response getById(String id) {
        Account account = accountDAO.getById(id);
        if(account == null) {
            return ResponseFactory.createUserNotFoundResponse(id);
        }
        return ResponseFactory.createResponseWithEntity(account);
    }

    public Response getByUsername(String username) {
        Account account = accountDAO.getByUsername(username);
        if(account == null) {
            return ResponseFactory.createUserNotFoundResponse(username);
        }
        return ResponseFactory.createResponseWithEntity(account);
    }

    public Response signUp(String email, String name, String password) {
        if (!validateInfo(email, name, password)) {
            return ResponseFactory.createSignUpInfoInvalidResponse();
        }
        if (accountDAO.hasEmail(email)) {
            return ResponseFactory.createSignUpConflictResponse(email);
        }
        String id = accountDAO.signUp(email, name, password);

        String username = createAndUpdateUsername(id, name);

        String key = accountDAO.getSecretKey(id);

        sendVerifyEmail(id, email, key);

        Account account = new Account();
        account.setId(id);
        account.setEmail(email);
        account.setUsername(username);
        account.setName(name);
        account.setRole(Role.LEARNER);

        return ResponseFactory.createResponseWithEntity(account);
    }

    private boolean validateInfo(String email, String name, String password) {
        return Validator.email(email) && Validator.name(name) && Validator.password(password);
    }

    private void sendVerifyEmail(String id, String email, String key) {
        EmailVerifier.sendVerifyEmail(id, email, key);
    }

    private String createAndUpdateUsername(String id, String name) {
        String username = createUsername(name);
        if(!accountDAO.updateUsername(id, username)) {
            username = createUsername(name, id);
            accountDAO.updateUsername(id, username);
        }
        return username;
    }

    private String createUsername(String name) {
        return name.replace(" ", "");
    }

    private String createUsername(String name, String id) {
        return createUsername(name).concat("-").concat(id);
    }

    public Response verifyEmail(String token) {
        String id = EmailVerifier.getId(token);
        if (accountDAO.checkEmailVerified(id)) {
            return ResponseFactory.createEmailVerifiedResponse();
        }

        String key = accountDAO.getSecretKey(id);

        if (!EmailVerifier.verifyToken(key, token)) {
            return ResponseFactory.createEmailVerifyTokenInvalidResponse();
        }

        accountDAO.verifyEmail(id);

        key = accountDAO.updateSecretKey(id);

        String jwt = JWTUtils.signForUser(key, id);

        return ResponseFactory.createResponseWithEntity("jwt", jwt);
    }

    public Response resendVerifyEmail(String id) {
        String email = accountDAO.getById(id).getEmail();

        if (accountDAO.checkEmailVerified(id)) {
            return ResponseFactory.createEmailVerifiedResponse();
        }

        String key = accountDAO.updateSecretKey(id);

        EmailVerifier.sendVerifyEmail(id, email, key);

        return ResponseFactory.createOkResponse();
    }

    public Response updateUsername(String id, String username, String password) {
        if (!accountDAO.checkPassword(id, password)) {
            return ResponseFactory.createWrongPasswordErrorMessage();
        }
        if(!Validator.username(username)) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        if(!accountDAO.updateUsername(id, username)) {
            return ResponseFactory.createUpdateConflictErrorMessage();
        }
        return ResponseFactory.createOkResponse();
    }

    public Response updateName(String id, String name, String password) {
        if (!accountDAO.checkPassword(id, password)) {
            return ResponseFactory.createWrongPasswordErrorMessage();
        }
        if(!Validator.name(name)) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        if(!accountDAO.updateName(id, name)) {
            return ResponseFactory.createUpdateConflictErrorMessage();
        }
        return ResponseFactory.createOkResponse();
    }

    public Response updatePassword(String id, String newPassword, String password) {
        if (!accountDAO.checkPassword(id, password)) {
            return ResponseFactory.createWrongPasswordErrorMessage();
        }
        if(!Validator.password(newPassword)) {
            return ResponseFactory.createUpdateInfoInvalidErrorMessage();
        }
        if(!accountDAO.updatePassword(id, newPassword)) {
            return ResponseFactory.createUpdateConflictErrorMessage();
        }
        return ResponseFactory.createOkResponse();
    }
}
