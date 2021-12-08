package com.soen387.usermanager;

import java.util.Random;

public class UserManager implements com.soen387.business.UserManager {

    public static String generateToken(){
        Random rd = new Random();
        String abc = "ABCDEFGHJKMNPQRSTVWXYZ0123456789";
        char character = 0;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) {
            character = abc.charAt(rd.nextInt(abc.length()));
            salt.append(character);
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
