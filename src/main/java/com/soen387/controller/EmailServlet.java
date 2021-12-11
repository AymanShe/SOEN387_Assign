package com.soen387.controller;

import com.soen387.business.PollManager;
import com.soen387.dataaccess.UserBaseFileLoader;
import com.soen387.emailer.GatewayEmailer;
import com.soen387.emailer.MessageType;
import com.soen387.model.Poll;
import com.soen387.usermanager.User;
import com.soen387.usermanager.UserBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.soen387.emailer.GatewayEmailer.sendEmail;

@WebServlet(name = "EmailServlet", value = {"/Email", "/email"})
public class EmailServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserBase userBase = new UserBase(new UserBaseFileLoader());
        //TODO Fix hardcode
        User receiver = userBase.getUserById(1);
        int messageType = 1;
        sendEmail(receiver, MessageType.Activate);

        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "index.jsp");
        dispatcher.forward(request, response);
    }
}
