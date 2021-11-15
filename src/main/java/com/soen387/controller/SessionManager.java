package com.soen387.controller;

import com.soen387.model.User;
import com.soen387.model.UserBase;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class SessionManager {
    public static boolean isUserAuthenticated(HttpSession session){
        User user = (User)session.getAttribute("UserID");
        if (user != null)
            return true;
        return false;
    }
    public static String getAuthenticatedUserName(HttpSession session){
        User user = (User)session.getAttribute("UserID");
        if (user == null)
            return null;
        return user.getName();
    }

    public static void loginUser(String enteredUsername, String enteredPassword, HttpSession session) {
        UserBase userBase = new UserBase();
        userBase.loadUserBase(Utility.readProperties("userbaseInfo.properties").getProperty("userbase.path"));

        if (userBase.login(enteredUsername, enteredPassword)) {
            session.setAttribute("ManagerAccess", "true");
            session.setAttribute("UserID", userBase.getUserByName(enteredUsername));
        }
    }

    public static void LogoutUser(HttpSession session) {
        session.removeAttribute("ManagerAccess");
        session.removeAttribute("UserID");
    }
}
