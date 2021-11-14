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

@WebServlet(name = "ManagePollServlet", value = "/manage/*")
public class ManagePollServlet extends HttpServlet {
    PollManager pollManager = new PollManager();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pollId = request.getParameter("poll_id");
        Poll poll = pollManager.getPoll(pollId);

        String pathInfo = request.getPathInfo();
        String action = pathInfo.substring(1);
        switch (action){
            case "run":
                pollManager.runPoll(poll);
                break;
            case "release":
                pollManager.realeasePoll(poll);
                break;
            case "unrelease":
                pollManager.unrealeasePoll(poll);
                break;
            case "close":
                pollManager.closePoll(poll);
                break;
            case "delete":
                pollManager.deletePoll(poll);
                response.sendRedirect(request.getContextPath() );
                return;
            default:
                break;
        }

        response.sendRedirect(request.getContextPath() + "/manage/" + poll.getPollId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //fetch the poll using the PollManager
        String pathInfo = request.getPathInfo();
        String pollId = pathInfo.substring(1);
        Poll poll = pollManager.getPoll(pollId);

        //pass the poll as a bean
        request.setAttribute("ManagedPoll", poll);

        //forward to the view page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "managepoll.jsp");
        dispatcher.forward(request, response);
    }
}
