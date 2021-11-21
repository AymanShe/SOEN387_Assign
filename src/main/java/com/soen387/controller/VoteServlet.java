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

@WebServlet(name = "com.soen387.controller.VoteServlet", value={"/vote/*", "/Vote/*"})
public class VoteServlet extends HttpServlet {
    PollManager pollManager = new PollManager();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && !pathInfo.isEmpty()){
            //capture the poll id
            String pollId = pathInfo.substring(1);


            //capture the choice
            String choiceNumber = request.getParameter("choice");

            //insert the vote into db
            try {
                pollManager.insertVote(pollId, choiceNumber);
            } catch (PollException e) {
                e.printStackTrace();
            }
            //TODO: redirect

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
