import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.soen387.poll.*;

@WebServlet(name = "pollManagerServlet", value = "/poll-manager-servlet")
public class PollManagerServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("GET: Poll Manager Servlet");
        //PollManager pollManager = new PollManager();
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("ManagerAccess"));
        if (session.getAttribute("ManagerAccess") == "true") {
            RequestDispatcher view = request.getRequestDispatcher("/pollManager.jsp");
            System.out.println(getServletContext().getAttribute("poll"));
            view.forward(request, response);
        } else {
            RequestDispatcher view = request.getRequestDispatcher("/pollManagerEntry.jsp");
            view.forward(request, response);
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = (String)request.getAttribute("action");
        System.out.println("POST: Poll Manager Servlet");

        String pathInfo = request.getPathInfo();

        if (pathInfo.compareToIgnoreCase("/confirmPassword") == 0) {
            confirmPassword(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Create") == 0) {
            createPoll(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Run") == 0) {
            runPoll(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Update") == 0) {
            updatePoll(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Clear") == 0) {
            clearPoll(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Release") == 0) {
            releasePoll(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Unrelease") == 0) {
            unreleasePoll(request, response);
        }
        else if (pathInfo.compareToIgnoreCase("/Close") == 0) {
            closePoll(request, response);
        }

    }

    private void confirmPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String enteredPassword = request.getParameter("password");
        if (enteredPassword.compareTo("iliketrains") == 0) {
            request.getSession().setAttribute("ManagerAccess", "true");
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void createPoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        String name = request.getParameter("pollName");
        String question = request.getParameter("pollQuestion");
        String[] choiceNames = request.getParameterValues("choiceName");
        String[] choiceDescs = request.getParameterValues("choiceDesc");
        Choice[] choices = new Choice[choiceNames.length];

        System.out.println("Name: " + name);
        System.out.println("Question: " + question);
        for (int i = 0; i < choiceNames.length; i++) {
            choices[i] = new Choice(choiceNames[i], choiceDescs[i]);
            System.out.println("Question #" + (i + 1) + ": [" + choiceNames[i] + "] " + choiceDescs[i]);
        }

        try {
            poll.createPoll(name, question, choices);
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");


    }

    private void runPoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        try {
            poll.runPoll();
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void updatePoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        String name = request.getParameter("pollName");
        String question = request.getParameter("pollQuestion");
        String[] choiceNames = request.getParameterValues("choiceName");
        String[] choiceDescs = request.getParameterValues("choiceDesc");
        Choice[] choices = new Choice[choiceNames.length];

        System.out.println("Name: " + name);
        System.out.println("Question: " + question);
        for (int i = 0; i < choiceNames.length; i++) {
            choices[i] = new Choice(choiceNames[i], choiceDescs[i]);
            System.out.println("Question #" + (i + 1) + ": [" + choiceNames[i] + "] " + choiceDescs[i]);
        }

        try {
            poll.updatePoll(name, question, choices);
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void clearPoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        try {
            poll.clearPoll();
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void releasePoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        try {
            poll.releasePoll();
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void unreleasePoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        try {
            poll.unreleasePoll();
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void closePoll(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PollManager poll = getPollManager();
        try {
            poll.closePoll();
        } catch (PollException e) {
            forwardError(request, response, e);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/PollManager");
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException, ServletException {
        request.setAttribute("error", e);
        System.out.println(e);
        request.getRequestDispatcher("../error.jsp").forward(request, response);

    }

    private PollManager getPollManager() {
        return (PollManager)getServletContext().getAttribute("poll");
    }



    public void destroy() {
    }
}