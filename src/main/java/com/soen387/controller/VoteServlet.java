package com.soen387.controller;

import com.soen387.business.PollException;
import com.soen387.business.PollManager;
import com.soen387.model.Poll;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "com.soen387.controller.VoteServlet", value = {"/vote/*", "/Vote/*"})
public class VoteServlet extends HttpServlet {
    PollManager pollManager = new PollManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && !pathInfo.isEmpty()) {
            //capture the pin id
            String enteresPinId = request.getParameter("pinId");
            //capture the poll id
            String pollId = pathInfo.substring(1);
            //capture the choice
            String choiceNumber = request.getParameter("choice");
            //insert the vote into db
            int pinId = 0;
            String status = "";
            try {
                //TODO: logic to createVote or updateVote
                if (enteresPinId == null || enteresPinId.equals("") || enteresPinId.equals("null")) {
                    pinId = pollManager.createVote(pollId, choiceNumber);
                    status = "new";
                } else {
                    pollManager.updateVote(enteresPinId, pollId, choiceNumber);
                    pinId = Integer.parseInt(enteresPinId);
                    status = "edit";
                }
            } catch (PollException e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath() + "/vote/" + pollId + "?pinId=" + pinId + "&choiceNumber=" + choiceNumber + "&status=" + status);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        //fetch the poll using the PollManager
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String pollId = pathInfo.substring(1);
            Poll poll = pollManager.getPoll(pollId);

            if (poll == null){
                response.sendRedirect(request.getContextPath() + "?error=Poll not found");
                return;
            }
            //only allow voting if the poll is running or released
            if (poll.getStatus() != Poll.PollStatus.running && poll.getStatus() != Poll.PollStatus.released) {
                response.sendRedirect(request.getContextPath() + "?error=You cannot access a poll that is not running or released");
                return;
            }

            //pass the poll as a bean
            request.setAttribute("ManagedPoll", poll);

            String choiceNumber = request.getParameter("choiceNumber");
            String pinId = request.getParameter("pinId");
            String status = request.getParameter("status");
            if (pinId != null && !pinId.isEmpty()) {
                request.setAttribute("choiceNumber", choiceNumber);
                request.setAttribute("pinId", pinId);
                request.setAttribute("status", status);
            }

            //forward to the view page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "vote.jsp");
            dispatcher.forward(request, response);
        } else {
            //TODO return a better feedback message
            response.sendRedirect(request.getContextPath());
        }


    }
}
