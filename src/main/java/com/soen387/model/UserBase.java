package com.soen387.model;

import com.soen387.controller.Utility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class UserBase {

    private LinkedList<User> userList;
    private int highestId = 0;

    public UserBase() {
        userList = new LinkedList<User>();
        highestId = 0;
    }

    public void loadUserBase(JSONArray userListJson) {
        userList = new LinkedList<User>();
        highestId = 0;

        String userListJsonString = "";

        System.out.println(userListJsonString);

        JSONParser parser = new JSONParser();

        for (int i = 0; i < userListJson.size(); i++) {
            JSONObject userJson = (JSONObject) userListJson.get(i);
            int id = ((Long)userJson.get("id")).intValue();
            String name = (String) userJson.get("name");
            String password = (String) userJson.get("password");
            String email = (String) userJson.get("email");

            if (id > highestId) highestId = id;

            userList.add(new User(id, name, password, email));
        }
    }

    public JSONArray toJson() {
        JSONArray userListJson = new JSONArray();
        for (User user : userList) {
            userListJson.add(user.toJson());
        }

        return userListJson;

    }

    public void createUser(String name, String password, String email) {
        boolean nameAvailable = true;
        for (User user : userList) {
            if (user.getName().compareToIgnoreCase(name) == 0) {
                nameAvailable = false;
                break;
            }
        }

        User newUser = new User(highestId+1, name, password, email);
        highestId++;
        userList.add(newUser);
    }

    public User getUserById(int id) {
        for (User user : userList) {
            if (id == user.getId()) {
                return user;
            }
        }

        return null;
    }

    public User getUserByName(String name) {
        for (User user : userList) {
            if (user.getName().compareToIgnoreCase(name) == 0) {
                return user;
            }
        }

        return null;
    }

    public boolean login(String username, String password) {
        User user = getUserByName(username);
        if (user == null) return false;
        return user.comparePassword(password);
    }

    public void hashAllPasswords() {
        for (User user : userList) {
            user.setPassword(user.getPassword());
        }
    }
}
