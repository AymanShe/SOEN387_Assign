package com.soen387.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", value = {"/Change", "/change"})
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredPassword = request.getParameter("password");

        //TODO validate the input for duplicates accounts and correct information entered
        //TODO call the business function that handles the action of signup

        //TODO remove the following code after dev
        System.out.println("user signup information is captured.. now change the password and update the user object in the session");
        System.out.println(String.format("Password: %s.", enteredPassword));


        //if success redirect
        response.sendRedirect(request.getContextPath() + "?info=Your password has been changed successfully");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionManager.isUserAuthenticated(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "?error=You need to login first");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "change.jsp");
        dispatcher.forward(request, response);
    }
}
