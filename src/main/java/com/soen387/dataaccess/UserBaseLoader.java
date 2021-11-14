package com.soen387.dataaccess;

import com.soen387.controller.Utility;
import com.soen387.model.UserBase;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserBaseLoader {

    private static UserBase userBase;

    private static String path = Utility.readProperties("userbaseInfo.properties").getProperty("userbase.path");

    public static UserBase getUserBase() {
        if (userBase == null) return loadUserBase();
        else return userBase;
    }

    private static UserBase loadUserBase() {
        String userListJsonString = "";
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter("\\Z");
            userListJsonString = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(userListJsonString);

        JSONParser parser = new JSONParser();

        try {
            JSONArray userListJson = (JSONArray) parser.parse(userListJsonString);

            userBase = new UserBase();
            userBase.loadUserBase(userListJson);
            return userBase;

        } catch (ParseException e) {
            userBase = null;
            e.printStackTrace();
            return null;
        }
    }

    public static void saveUserBase() {
        JSONArray userListJson = getUserBase().toJson();
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(userListJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
