package com.soen387.controller;

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

@WebServlet(name="CreatePollServlet" , value = {"/Create", "/create"})
public class CreatePollServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String question = request.getParameter("question");

        Poll poll = new Poll();
        poll.setName(name);
        poll.setQuestion(question);

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
            pollManager.createPoll(poll);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/manage/" + poll.getPollId());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!SessionManager.isUserAuthenticated(request.getSession())){
            response.sendRedirect(request.getContextPath() + "/Login?returnurl=create");
            return;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "createpoll.jsp");
        dispatcher.forward(request, response);
    }
}
