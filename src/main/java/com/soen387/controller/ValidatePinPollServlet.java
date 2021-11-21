package com.soen387.controller;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ValidatePinServlet", value = {"/ValidatePinPoll", "/validatepinpoll"})
public class ValidatePinPollServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredPinId = request.getParameter("pinId");
        String enteredPollId = request.getParameter("pollId");
        //TODO: need to pre-validate if pin_id & poll_id exist

        if (enteredPinId == null) {
            //TODO: Search DB if poll exists (used hardcode to test)
            boolean pollExists = true;
            if (pollExists) {
                //TODO: Do vote with createVote (generate new pin)
                String returnUrl = request.getParameter("returnurl");
                if (returnUrl != null && !returnUrl.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/" + returnUrl);
                } else {
                    response.sendRedirect(request.getContextPath());
                }
            } else {
                //TODO: Handle invalid Poll entry
                throw new IOException();
            }
        } else {
            //TODO: Search DB if pin & poll combo exists (used hardcore to test)
            boolean voteExists = true;
            if (voteExists) {
                //TODO: Do vote with updateVote (use supplied pin)
                response.sendRedirect(request.getContextPath() + "/vote/" + enteredPollId);
            } else {
                //TODO: Handle invalid Pin & Poll entry
                throw new IOException();
            }
        }
    }

    //TODO: Not properly implemented
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "index.jsp");
        dispatcher.forward(request, response);
    }
}
