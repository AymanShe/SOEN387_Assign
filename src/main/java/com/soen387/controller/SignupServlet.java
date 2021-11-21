package com.soen387.controller;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SignupServlet", value = {"/Signup", "/signup"})
public class SignupServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UnsupportedOperationException e = new UnsupportedOperationException("Sign Up is not available at the current time.");
        request.setAttribute("error", e);
        request.getRequestDispatcher(Constants.ViewsBaseLink + "error.jsp").forward(request, response);
    }
}
