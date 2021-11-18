package com.soen387.controller;

import com.soen387.business.PollException;
import com.soen387.business.PollManager;
import com.soen387.model.Choice;
import com.soen387.model.Poll;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "EditPollServlet", value = {"/edit/*","/Edit/*"})
public class EditPollServlet extends HttpServlet {
    PollManager pollManager = new PollManager();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String question = request.getParameter("question");
        String poll_id = request.getParameter("poll_id");

        Poll poll = new Poll();
        poll.setName(name);
        poll.setQuestion(question);
        poll.setPollId(poll_id);

        poll.setCreatedBy(SessionManager.getAuthenticatedUserName(request.getSession()));

        String[] choiceNames = request.getParameterValues("choiceName");
        String[] choiceDescs = request.getParameterValues("choiceDesc");
        Choice[] choices = new Choice[choiceNames.length];

        for (int i = 0; i < choiceNames.length; i++) {
            choices[i] = new Choice(choiceNames[i], choiceDescs[i]);
            choices[i].setNumber(i+1);
            System.out.println("Question #" + (i + 1) + ": [" + choiceNames[i] + "] " + choiceDescs[i]);
        }

        poll.setChoices(choices);

        //TODO validate the input

        PollManager pollManager = new PollManager();
        try {
            pollManager.editPoll(poll);
        } catch (PollException e) {
            //TODO handle exception correctly
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/manage/" + poll.getPollId());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if(!SessionManager.isUserAuthenticated(request.getSession())){
            String pollId = "/";
            if (pathInfo != null && !pathInfo.isEmpty()){
                pollId += pathInfo.substring(1);
            }else{
                pollId = "";
            }
            response.sendRedirect(request.getContextPath() + "/Login?returnurl=edit" + pollId);
            return;
        }
        //fetch the poll using the PollManager
        if (pathInfo != null && !pathInfo.isEmpty()){
            String pollId = pathInfo.substring(1);
            Poll poll = pollManager.getPoll(pollId);

            //pass the poll as a bean
            request.setAttribute("ManagedPoll", poll);

            //forward to the view page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "editpoll.jsp");
            dispatcher.forward(request, response);
        }
        //TODO handle when pollid is not present
//        else{
//            //fetch user id
//            String userName = SessionManager.getAuthenticatedUserName(request.getSession());
//
//            //fetch polls
//            List<Poll> polls = pollManager.getPollsByUserName(userName);
//
//            //pass the polls as a bean
//            request.setAttribute("polls", polls);
//
//            //forward
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/" + Constants.ViewsBaseLink + "polllist.jsp");
//            dispatcher.forward(request, response);
//        }
















        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "createpoll.jsp");
        dispatcher.forward(request, response);
    }
}
