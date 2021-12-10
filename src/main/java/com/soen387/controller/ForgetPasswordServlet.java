package com.soen387.controller;

import com.soen387.dataaccess.UserBaseFileLoader;
import com.soen387.emailer.GatewayEmailer;
import com.soen387.usermanager.User;
import com.soen387.usermanager.UserBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ForgetPasswordServlet", value = {"/Forget", "/forget"})
public class ForgetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredUsername = request.getParameter("username");

        //TODO validate the input for existence of the account
        //TODO call the business function that handles the action of signup

        //TODO remove the following code after dev
        System.out.println("user information is captured.. now disable the account and send a forget password link to the user's email");
        System.out.println(String.format("Username: %s.", enteredUsername));

        UserBase userBase = new UserBase(new UserBaseFileLoader());
        User user = userBase.getUserByName(enteredUsername);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "?error=The account does not exist.");
            return;
        }

        user.forgetPassword();
        userBase.saveUserBase();
        GatewayEmailer.sendEmail(user, 2);
        response.sendRedirect(request.getContextPath() + "?info=An email has been sent. Please check your emails for the procedure to reset your password.");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SessionManager.isUserAuthenticated(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "?error=You're already signed in.");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "forget.jsp");
        dispatcher.forward(request, response);

    }
}
