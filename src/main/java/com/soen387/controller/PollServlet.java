package com.soen387.controller;

import com.soen387.dataaccess.PollDao;
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

@WebServlet(value = "/create")
public class PollServlet extends HttpServlet {

    private PollDao pollDao = new PollDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String question = request.getParameter("question");

        Poll poll = new Poll();
        poll.setName(name);
        poll.setQuestion(question);

        Choice choice1 = new Choice(request.getParameter("choiceName1"), request.getParameter("choiceDesc1"));
        choice1.setNumber(1);
        Choice choice2 = new Choice(request.getParameter("choiceName2"), request.getParameter("choiceDesc2"));
        choice2.setNumber(2);
        Choice choice3 = new Choice(request.getParameter("choiceName3"), request.getParameter("choiceDesc3"));
        choice3.setNumber(3);

        poll.setChoices(new Choice[]{choice1,choice2,choice3});

        //TODO validate the input

        try {
            pollDao.createPoll(poll);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "polldeatil.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher(Constants.ViewsBaseLink + "createpoll.jsp");
        dispatcher.forward(request, response);
    }
}
