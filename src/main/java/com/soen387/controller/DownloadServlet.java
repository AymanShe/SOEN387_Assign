package com.soen387.controller;

import com.soen387.business.PollManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DownloadServlet", value = "/download")
public class DownloadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PollManager pollManager = (PollManager)getServletContext().getAttribute("poll");
        response.setDateHeader("Expires", 0);
        PrintWriter out = response.getWriter();
        try {
            StringBuilder fileName = new StringBuilder();
            pollManager.downloadPollDetails(out, fileName);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        } catch (Exception e) {
            request.setAttribute("error", e);
            System.out.println(e);
            request.getRequestDispatcher(Constants.ViewsBaseLink + "error.jsp").forward(request, response);
            return;
        }
    }
}
