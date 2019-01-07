package com.lyonguyen.esky.logic.dao;

import com.lyonguyen.esky.logic.models.Account;
import com.lyonguyen.esky.database.DataProvider;
import com.lyonguyen.esky.database.DataRow;
import com.lyonguyen.esky.database.DataTable;

public class AccountDAO extends DataAccessObject {

    public AccountDAO(DataProvider dataProvider) {
        super(dataProvider);
    }

    public boolean has(String id) {
        Integer decodedId = encoder.decode(id);
        Object result = executeScalar("select id from accounts where id = ?", decodedId);
        return result != null;
    }

    public boolean hasEmail(String email) {
        Integer result = executeScalar(Integer.class, "select check_email_exists(?)", email);
        return result == 1;
    }

    public boolean hasUsername(String username) {
        Integer result = executeScalar(Integer.class, "select check_username_exists(?)", username);
        return result == 1;
    }

    public String signUp(String email, String name, String password) {
        Integer result = executeScalar(Integer.class, "call sign_up(?, ?, ?)", email, name, password);
        if(result < 0) {
            return null;
        }
        return encoder.encode(result);
    }

    public String signIn(String email, String password) {
        Integer result = executeScalar(Integer.class, "call sign_in(?, ?)", email, password);
        if(result < 0) {
            return null;
        }
        return encoder.encode(result.intValue());
    }

    public boolean checkPassword(String id, String password) {
        Integer decodedId = encoder.decode(id);
        Integer result = executeScalar(Integer.class, "select check_password(?, ?)", decodedId, password);
        return result == 1;
    }

    public String getSecretKey(String id) {
        Integer decodedId = encoder.decode(id);
        String secretKey = executeScalar(String.class, "call get_secret_key(?)", decodedId);
        return secretKey;
    }

    public String updateSecretKey(String id) {
        Integer decodedId = encoder.decode(id);
        String secretKey = executeScalar(String.class, "call update_secret_key(?)", decodedId);
        return secretKey;
    }

    public boolean checkEmailVerified(String id) {
        Integer decodedId = encoder.decode(id);
        Integer result = executeScalar(Integer.class, "select verified from accounts where id = ?", decodedId);
        if(result == null) {
            return false;
        }
        return result != 0;
    }

    public boolean verifyEmail(String id) {
        Integer decodedId = encoder.decode(id);
        Integer result = executeUpdate("update accounts set verified = 1 where id = ?", decodedId);
        if(result == 0) {
            return false;
        }
        return true;
    }

    public Account getById(String id) {
        Integer decodedId = encoder.decode(id);
        DataTable table = executeQuery("select id, email, username, name, role from accounts where id = ?", decodedId);
        if(table.isEmpty()) {
            return null;
        }
        DataRow row = table.row(0);
        Account account = mapper.mapDataRow(row, Account.class);
        return encoder.encodeObject(account);
    }

    public Account getByUsername(String username) {
        DataTable table = executeQuery("select id, email, username, name, role from accounts where username = ?", username);
        if(table.isEmpty()) {
            return null;
        }
        DataRow row = table.row(0);
        Account account = mapper.mapDataRow(row, Account.class);
        return encoder.encodeObject(account);
    }

    public boolean updateUsername(String id, String username) {
        Integer decodedId = encoder.decode(id);
        Integer result = executeUpdate("update accounts set username = ? where id = ?", username, decodedId);
        return result != 0;
    }

    public boolean updateName(String id, String name) {
        Integer decodedId = encoder.decode(id);
        Integer result = executeUpdate("update accounts set name = ? where id = ?", name, decodedId);
        return result != 0;
    }

    public boolean updatePassword(String id, String password) {
        Integer decodedId = encoder.decode(id);
        Integer result = executeUpdate("update accounts set password = ? where id = ?", password, decodedId);
        return result != 0;
    }
}
