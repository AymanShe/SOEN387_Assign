package com.soen387.dataaccess;

import com.soen387.controller.Utility;
import com.soen387.usermanager.UserBase;
import com.soen387.usermanager.UserBaseLoader;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class UserBaseFileLoader implements UserBaseLoader {

    private static String path = Utility.readProperties("userbaseInfo.properties").getProperty("userbase.path");

    public JSONArray loadUserBase() {
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
            return userListJson;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveUserBase(JSONArray userListJson) {
        //JSONArray userListJson = getUserBase().toJson();
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(userListJson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
