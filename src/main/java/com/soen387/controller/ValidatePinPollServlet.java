package com.soen387.controller;


import com.soen387.business.PollManager;
import com.soen387.model.Poll;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ValidatePinPollServlet", value = {"/ValidatePinPoll", "/validatepinpoll"})
public class ValidatePinPollServlet extends HttpServlet {
    PollManager pollManager = new PollManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String enteredPinId = request.getParameter("pinId");
        String enteredPollId = request.getParameter("pollId");

        if (enteredPinId == null || enteredPinId.isEmpty()) {
            //shift the responsibility to the VoteServlet
            response.sendRedirect(request.getContextPath() + "/vote/" + enteredPollId);
        } else {
            int choiceNumber = pollManager.getChoiceNumber(enteredPinId, enteredPollId);
            boolean voteExists = false;
            if (choiceNumber > 0) {
                voteExists = true;
            }
            if (voteExists) {
                request.setAttribute("choiceNumber", choiceNumber);
                request.setAttribute("pinId", enteredPinId);

                response.sendRedirect(request.getContextPath() + "/vote/" + enteredPollId + "?pinId=" + enteredPinId + "&choiceNumber=" + choiceNumber);
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "?error=Vote not found for the provided PIN ID");
                return;
            }
        }
    }
}
