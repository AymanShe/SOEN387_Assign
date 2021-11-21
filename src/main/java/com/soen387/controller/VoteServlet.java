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

@WebServlet(name = "com.soen387.controller.VoteServlet", value={"/vote/*", "/Vote/*"})
public class VoteServlet extends HttpServlet {
    PollManager pollManager = new PollManager();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && !pathInfo.isEmpty()){
            //capture the pin id
            String pinId = request.getParameter("pinId");
            //capture the poll id
            String pollId = pathInfo.substring(1);
            //capture the choice
            String choiceNumber = request.getParameter("choice");
            //insert the vote into db
            try {
                //TODO: logic to createVote or updateVote
                if (pinId == null || pinId.equals("") || pinId.equals("null")) {
                    pollManager.createVote(pollId, choiceNumber);
                } else {
                    pollManager.updateVote(pinId, pollId, choiceNumber);
                }
            } catch (PollException e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        //fetch the poll using the PollManager
        if (pathInfo != null && !pathInfo.isEmpty()){
            String pollId = pathInfo.substring(1);
            Poll poll = pollManager.getPoll(pollId);


            //pass the poll as a bean
            request.setAttribute("ManagedPoll", poll);

            String choiceNumber = request.getParameter("choiceNumber");
            String pinId = request.getParameter("pinId");
            if (pinId != null && !pinId.isEmpty()) {
                request.setAttribute("choiceNumber", choiceNumber);
                request.setAttribute("pinId", pinId);
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
