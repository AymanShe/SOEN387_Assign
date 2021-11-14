package com.soen387.model;

import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

public class User {

    private int id;
    private String name;
    private String password;
    private String email;

    public User() {
        id = 0;
        name = "";
        password = "";
        email = "";
    }

    public User(int _id, String _name, String _password, String _email) {
        id = _id;
        name = _name;
        password = _password;
        email = _email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int _id) {
        id = _id;
    }

    public void setName(String _name) {
        name = _name;
    }

    public void setPassword(String _password) {
        password = encryptPassword(_password);
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public boolean comparePassword(String input) {
        System.out.println(encryptPassword(input));
        return BCrypt.checkpw(input, password);
    }

    public JSONObject toJson() {
        JSONObject userJson = new JSONObject();
        userJson.put("id", id);
        userJson.put("name", name);
        userJson.put("password", password);
        userJson.put("email", email);

        return userJson;
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
