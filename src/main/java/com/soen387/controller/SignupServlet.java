package com.soen387.controller;


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

        //TODO validate the input for duplicates accounts and correct information entered
        //TODO call the business function that handles the action of signup

        //TODO remove the following code after dev
        System.out.println("user signup information is captured.. now save this info generate the token and send an email and redirect");
        System.out.println(String.format("Username: %s. password: %s. email: %s", enteredUsername, enteredPassword, enteredEmail));


        //if success redirect
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "activate.jsp");
        dispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO check if the user is already signed in
        if (SessionManager.isUserAuthenticated(request.getSession())) {
            response.sendRedirect(request.getContextPath() + "?error=You're already signed in. You have to logout to create a new account.");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "signup.jsp");
        dispatcher.forward(request, response);
    }
}
