package com.soen387.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public abstract class Utility {
    public static String generatePollId(){
        Random rd = new Random();
        String abc = "ABCDEFGHJKMNPQRSTVWXYZ0123456789";
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

    public static String generatePinId(){
        Random rd = new Random();
        String abc = "0123456789";
        char character = 0;
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) {
            character = abc.charAt(rd.nextInt(abc.length()));
            salt.append(character);
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static Properties readProperties(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(fileName);
        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
