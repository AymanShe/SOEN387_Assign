package com.soen387.usermanager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.LinkedList;

public class UserBase {

    private LinkedList<User> userList;
    private int highestId = 0;
    private UserBaseLoader loader;

    public UserBase() {
        userList = new LinkedList<User>();
        highestId = 0;
    }

    public UserBase(UserBaseLoader _loader) {
        userList = new LinkedList<User>();
        highestId = 0;
        loader = _loader;
        loadUserBase();
    }

    public void loadUserBase() {
        if (loader == null) return;
        JSONArray userListJson = loader.loadUserBase();

        userList = new LinkedList<User>();
        highestId = 0;

        //String userListJsonString = "";
        //System.out.println(userListJsonString);

        JSONParser parser = new JSONParser();

        for (int i = 0; i < userListJson.size(); i++) {
            JSONObject userJson = (JSONObject) userListJson.get(i);
            int id = ((Long)userJson.get("id")).intValue();
            String name = (String) userJson.get("name");
            String password = (String) userJson.get("password");
            String email = (String) userJson.get("email");
            boolean activated = (boolean) userJson.get("activated");
            String activationCode = (String) userJson.get("activationCode");

            if (id > highestId) highestId = id;

            userList.add(new User(id, name, password, email, activated, activationCode));
        }
    }

    public void saveUserBase() {
        if (loader == null) return;

        loader.saveUserBase(toJson());
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
