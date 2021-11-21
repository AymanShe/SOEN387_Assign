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
import java.io.PrintWriter;

@WebServlet(name = "com.soen387.controller.VoteServlet")
public class VoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get the poll manager
        PollManager pollManager = (PollManager)getServletContext().getAttribute("poll");

        //get the vote attribute
        //get choice
        String voteValue = request.getParameter("choice");
        int voteValueAsInt = Integer.parseInt(voteValue);
        //get session id
        String sessionId = request.getRequestedSessionId();
        //record the vote
        try {
            pollManager.vote(sessionId, voteValueAsInt);
            //return
            request.getRequestDispatcher(Constants.ViewsBaseLink + "vote.jsp").forward(request, response);
        } catch (PollException e) {
            request.setAttribute("error", e);
            System.out.println(e);
            request.getRequestDispatcher(Constants.ViewsBaseLink + "error.jsp").forward(request, response);
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

            //forward to the view page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "vote.jsp");
            dispatcher.forward(request, response);
        } else {
            //TODO return a better feedback message
            response.sendRedirect(request.getContextPath());
        }


    }
}
