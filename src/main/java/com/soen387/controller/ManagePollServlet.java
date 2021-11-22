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
import java.util.List;

@WebServlet(name = "ManagePollServlet", value = {"/manage/*", "/Manage/*"})
public class ManagePollServlet extends HttpServlet {
    PollManager pollManager = new PollManager();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pollId = request.getParameter("poll_id");
        Poll poll = pollManager.getPoll(pollId);

        String pathInfo = request.getPathInfo();
        String action = pathInfo.substring(1);
        switch (action.toLowerCase()) {
            case "run":
                pollManager.runPoll(poll);
                break;
            case "release":
                pollManager.releasePoll(poll);
                break;
            case "unrelease":
                pollManager.unreleasePoll(poll);
                break;
            case "close":
                pollManager.closePoll(poll);
                break;
            case "delete":
                pollManager.deletePoll(poll);
                response.sendRedirect(request.getContextPath() + "/Manage");
                return;
            case "edit":
                response.sendRedirect(request.getContextPath() + "/edit/" + poll.getPollId());
                return;
            default:
                break;
        }

        response.sendRedirect(request.getContextPath() + "/manage/" + poll.getPollId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (!SessionManager.isUserAuthenticated(request.getSession())) {
            String pollId = "/";
            if (pathInfo != null && !pathInfo.isEmpty()) {
                pollId += pathInfo.substring(1);
            } else {
                pollId = "";
            }
            response.sendRedirect(request.getContextPath() + "/Login?returnurl=manage" + pollId);
            return;
        }
        //fetch the poll using the PollManager
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String pollId = pathInfo.substring(1);
            Poll poll = pollManager.getPoll(pollId);
            //check if the logged user is the creator
            if (!SessionManager.getAuthenticatedUserName(request.getSession()).equalsIgnoreCase(poll.getCreatedBy())){
                response.sendRedirect(request.getContextPath() + "?error=You cannot access a poll that is not created by you");
                return;
            }

            //pass the poll as a bean
            request.setAttribute("ManagedPoll", poll);

            List<String> allowedActions = pollManager.getAllowedActions(poll);
            request.setAttribute("allowedActions", allowedActions);

            //forward to the view page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "managepoll.jsp");
            dispatcher.forward(request, response);
        } else {
            //fetch user id
            String userName = SessionManager.getAuthenticatedUserName(request.getSession());

            //fetch polls
            List<Poll> polls = pollManager.getPollsByUserName(userName);

            //pass the polls as a bean
            request.setAttribute("polls", polls);

            //forward
            RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "polllist.jsp");
            dispatcher.forward(request, response);
        }

    }
}
