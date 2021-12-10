package com.soen387.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserActivationServlet", value = {"/Activate", "/activate"})
public class UserActivationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String token = request.getParameter("token");

        //TODO call the business function that will handle the activation action

        //if no error redirect to home page
        response.sendRedirect(request.getContextPath() + "?info=Your account is now activated");
        return;
    }
}
