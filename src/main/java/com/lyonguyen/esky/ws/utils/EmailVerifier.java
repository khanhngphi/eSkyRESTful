package com.lyonguyen.esky.ws.utils;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EmailVerifier {

    private static final String USER_NAME = "esky.verify";

    private static final String PASSWORD = "eskyemailverify";

    private static final String EMAIL_TITLE = "Verify your email on eSky ^^";

    private static final String EMAIL_MESSAGE = "Click this link to verify your email (if this wasn't you don't click): ";

    private static final String VERIFY_PATH = "http://locahost:4200/verify/";

    private static boolean sendEmail(String email, String title, String message) {
        try {
            GoogleMail.send(USER_NAME, PASSWORD, email, title, message);
            return true;
        } catch (AddressException ex) {
            ex.printStackTrace();
            return false;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean sendVerifyEmail(String uid, String email, String key) {
        String token = generateVerifyToken(uid, key);
        String verifyLink = VERIFY_PATH + token;
        return sendEmail(email, EMAIL_TITLE, EMAIL_MESSAGE + verifyLink);
    }

    public static boolean sendVerifyEmail(String uid, String email, String key, String path) {
        String token = generateVerifyToken(uid, key);
        String verifyLink = path + token;
        return sendEmail(email, EMAIL_TITLE, EMAIL_MESSAGE + verifyLink);
    }

    public static String generateVerifyToken(String id, String key) {
        String token = sha256(key);
        return token.concat("." + id);
    }

    public static boolean verifyToken(String key, String token) {
        token = token.substring(0, 64);
        String hash = sha256(key);
        return token.equals(hash);
    }

    public static String getId(String token) {
        if(token.length() != 75) {
            return null;
        }
        return token.substring(65, 75);
    }

    private static String sha256(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(key.getBytes("UTF-8"));
            messageDigest.reset();
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
