package com.soen387.controller;


import com.soen387.dataaccess.UserBaseFileLoader;
import com.soen387.emailer.GatewayEmailer;
import com.soen387.usermanager.DuplicateUserException;
import com.soen387.usermanager.User;
import com.soen387.usermanager.UserBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignupServlet", value = {"/Signup", "/signup"})
public class SignupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredUsername = request.getParameter("username");
        String enteredPassword = request.getParameter("password");
        String enteredEmail = request.getParameter("email");


        UserBase userBase = new UserBase(new UserBaseFileLoader());
        try {
            userBase.createUser(enteredUsername, enteredPassword, enteredEmail);
            userBase.saveUserBase();
        }
        catch (DuplicateUserException e) {
            response.sendRedirect(request.getContextPath() + "?error=The username has already been taken.");
        }

        User user = userBase.getUserByName(enteredUsername);

        GatewayEmailer.sendEmail(user, 1);

        System.out.println("user signup information is captured.. now save this info generate the token and send an email and redirect");
        System.out.println(String.format("Username: %s. password: %s. email: %s", enteredUsername, enteredPassword, enteredEmail));

        response.sendRedirect(request.getContextPath() + "?info=Your account has been created. Please check your email to activate your account.");
        //if success redirect
        //RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "activate.jsp");
        //dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SessionManager.isUserAuthenticated(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "?error=You're already signed in. You have to logout to create a new account.");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "signup.jsp");
        dispatcher.forward(request, response);
    }
}
