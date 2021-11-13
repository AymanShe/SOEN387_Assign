package com.soen387.controller;

import com.soen387.business.PollManager;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "downloadServlet", value = "/Download")
public class DownloadPollServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PollManager pollManager = getPollManager();
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

    private PollManager getPollManager() {
        return (PollManager)getServletContext().getAttribute("poll");
    }

    public void destroy() {
    }
}