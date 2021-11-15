package com.soen387.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = {"/Login", "/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredUsername = request.getParameter("username");
        String enteredPassword = request.getParameter("password");
        SessionManager.loginUser(enteredUsername, enteredPassword, request.getSession());

        if (SessionManager.isUserAuthenticated(request.getSession())) {
            String returnUrl = request.getParameter("returnurl");
            if (returnUrl != null && !returnUrl.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/" + returnUrl);
            } else {
                response.sendRedirect(request.getContextPath());
            }
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "login.jsp");
        dispatcher.forward(request, response);
    }
}
