package com.soen387.controller;

import com.soen387.dao.PollDao;
import com.soen387.poll.Poll;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "/create")
public class PollServlet extends HttpServlet {

    private PollDao pollDao = new PollDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String question = request.getParameter("question");

        Poll poll = new Poll();
        poll.setName(name);
        poll.setQuestion(question);

        try {
            pollDao.createPoll(poll);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("polldeatil.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());

        RequestDispatcher dispatcher = request.getRequestDispatcher("createpoll.jsp");
        dispatcher.forward(request, response);
    }
}
