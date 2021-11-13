package com.soen387.controller;

import java.util.Random;

public abstract class Utility {
    public static String generatePollId(){
        Random rd = new Random();
        String abc = "abcdefghjkmnpqrstvwxyz0123456789";
        char character = 0;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            character = abc.charAt(rd.nextInt(abc.length()));
            salt.append(character);
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}
