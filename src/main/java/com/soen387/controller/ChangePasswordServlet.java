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

@WebServlet(name = "ChangePasswordServlet", value = {"/Change", "/change"})
public class ChangePasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (SessionManager.isUserAuthenticated(request.getSession())) {
            String enteredPassword = request.getParameter("password");
            //TODO validate the input

            UserBase userBase = new UserBase(new UserBaseFileLoader());
            User user = userBase.getUserByName(SessionManager.getAuthenticatedUserName(request.getSession()));
            user.setPassword(enteredPassword);
            userBase.saveUserBase();
            response.sendRedirect(request.getContextPath() + "?info=Your password has been changed successfully");
        } else {
            String enteredPassword = request.getParameter("password");
            //TODO validate the input
            String username = request.getParameter("username");
            String token = request.getParameter("token");

            if (username != null && !username.isEmpty() && token != null && !token.isEmpty()) {
                UserBase userBase = new UserBase(new UserBaseFileLoader());
                User user = userBase.getUserByName(username);
                if (user != null && user.activate(token)) {
                    user.setPassword(enteredPassword);
                    userBase.saveUserBase();
                    response.sendRedirect(request.getContextPath() + "?info=Your password has been changed successfully. You can login using the new password.");
                } else {
                    response.sendRedirect(request.getContextPath() + "?error=Could not activate the account. The token does not match");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "?error=Something went wrong. Try again later.");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String token = request.getParameter("token");

        if (username != null && !username.isEmpty() && token != null && !token.isEmpty()) {
            UserBase userBase = new UserBase(new UserBaseFileLoader());
            User user = userBase.getUserByName(username);
            if (!user.compareToken(token)) {
                response.sendRedirect(request.getContextPath() + "?error=Could not accept your request. The token does not match");
                return;
            }
            request.setAttribute("token", token);
            request.setAttribute("username", username);
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "change.jsp");
            dispatcher.forward(request, response);
        } else {
            if (!SessionManager.isUserAuthenticated(request.getSession())) {
                response.sendRedirect(request.getContextPath() + "?error=You need to login first");
                return;
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "change.jsp");
            dispatcher.forward(request, response);
        }
    }
}
