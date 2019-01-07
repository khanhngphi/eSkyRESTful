package com.lyonguyen.esky.ws.utils;

import com.lyonguyen.esky.logic.dao.DatabaseContext;

public class WSTester {

    public static void main(String[] args) {

        DatabaseContext db = DatabaseContext.getInstance();
        /*try {
            GoogleMail.send("esky.verify", "eskyemailverify",
                    "mr.locasdfg@gmail.com", "Verify you email", "Please verify your email!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

        /*String jwt = JWTUtils.sign("123", "456", "admin", new Date(System.currentTimeMillis() + 99999999999L));
        System.out.println(jwt);
        System.out.println(JWTUtils.verify("123", jwt).getClaim("uid").asInt());*/

        /*String token = EmailVerifier.generateVerifyToken(HashIdsWrapper.encode(54), "4284104067");
        System.out.println(token);

        System.out.println(EmailVerifier.verifyToken("4284104067", token));*/

        //System.out.println(JWTUtils.verify("5848829696", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJCb3gxdzcxQUc3Iiwicm9sZSI6InVzZXIiLCJpc3MiOiJlc2t5IiwiZXhwIjoxNTA5NTU1NjAwfQ.eZ4PgACKOBnzD_sy0p2ZZx74rJyqONm8JqdS1ZOssqs").getClaim("uid").asString());

        /*try {
            System.out.println(JSONUtils.asJSON("jwt", 35));
        } catch (IOException e) {
            e.printStackTrace();
        }*/



        /*System.out.println(JWTUtils.sign("5848829696", HashIdsWrapper.encode(12), Role.ADMIN));
        System.out.println(db.getRole(HashIdsWrapper.encode(12)));*/

    }
}