package com.soen387.controller;

import com.soen387.dataaccess.UserBaseFileLoader;
import com.soen387.usermanager.User;
import com.soen387.usermanager.UserBase;

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

        System.out.println(username);

        UserBase userBase = new UserBase(new UserBaseFileLoader());
        if (token == null) {
            response.sendRedirect(request.getContextPath() + "?error=Missing token.");
            return;
        }
        User user = userBase.getUserByName(username);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "?error=Account does not exist.");
            return;
        } else if (user.isActivated()) {
            response.sendRedirect(request.getContextPath() + "?error=The account is already activated.");
            return;
        }

        if (user.activate(token)) {
            response.sendRedirect(request.getContextPath() + "?info=Your account has successfully been activated.");
            userBase.saveUserBase();
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "?error=Could not activate the account. The token does not match");
            return;
        }
    }
}
