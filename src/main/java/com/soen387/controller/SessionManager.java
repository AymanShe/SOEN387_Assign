package com.soen387.controller;

import com.soen387.dataaccess.UserBaseLoader;
import com.soen387.model.User;
import com.soen387.model.UserBase;

import javax.servlet.http.HttpSession;
import java.util.Map;

public abstract class SessionManager {
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
        UserBase userBase = UserBaseLoader.getUserBase();

        if (userBase.login(enteredUsername, enteredPassword)) {
            session.setAttribute("ManagerAccess", "true");
            session.setAttribute("UserID", userBase.getUserByName(enteredUsername));
        }
    }
    public static void LogoutUser(HttpSession session) {
        session.removeAttribute("ManagerAccess");
        session.removeAttribute("UserID");
    }

    public static void SetPinPollId(HttpSession session, String pollId, String pinId) {
        session.setAttribute("PollId", pollId);
        session.setAttribute("PinId", pinId);
    }

    public static boolean ValidatePinPollId(HttpSession session, String pollId, String pinId) {
        if (session.getAttribute("PollId") != pollId || session.getAttribute("PinId") != pinId) {
            return false;
        }

        return true;
    }
}
