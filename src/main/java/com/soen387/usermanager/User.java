package com.soen387.usermanager;

import org.json.simple.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Random;

public class User {

    private int id;
    private String name;
    private String password;
    private String email;
    private boolean activated;
    private String activationCode;

    public User() {
        id = 0;
        name = "";
        password = "";
        email = "";
        activated = false;
        activationCode = "";
    }

    // For signing up
    public User(int _id, String _name, String _password, String _email) {
        id = _id;
        name = _name;
        password = encryptPassword(_password);
        email = _email;
        activated = false;
        activationCode = generateActivationCode();
    }

    // For loading a user from database
    public User(int _id, String _name, String _password, String _email, boolean _isActivated, String _activationString) {
        id = _id;
        name = _name;
        password = _password;
        email = _email;
        activated = _isActivated;
        activationCode = _activationString;
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

    public String getActivationCode() {
        return activationCode;
    }

    public void setId(int _id) {
        id = _id;
    }

    public void setName(String _name) {
        name = _name;
    }

    // Set a new password. The password will be encrypted!
    public void setPassword(String _password) {
        password = encryptPassword(_password);
    }

    public void setEmail(String _email) {
        email = _email;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean activate(String code) {
        if (code.equals(activationCode)) {
            activationCode = "";
            activated = true;
            return true;
        }

        return false;
    }

    public void forgetPassword() {
        activationCode = generateActivationCode();
    }

    public String getNewPassword(String code) {
        if (code.equals(activationCode)) {
            String newPassword = generatePassword();
            setPassword(newPassword);
            activationCode = "";
            return newPassword;
        }
        else {
            return null;
        }

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
        userJson.put("activated", activated);
        userJson.put("activationCode", activationCode);

        return userJson;
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private String generateActivationCode(){
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

    private String generatePassword(){
        Random rd = new Random();
        String abc = "ABCDEFGHJKMNPQRSTVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?$%&-";
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
