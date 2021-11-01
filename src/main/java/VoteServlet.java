import com.soen387.poll.PollException;
import com.soen387.poll.PollManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "VoteServlet")
public class VoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //get the poll manager
        PollManager pollManager = (PollManager)getServletContext().getAttribute("poll");

        //get the vote attribute
        //get choice
        String voteValue = request.getParameter("choice");
        int voteValueAsInt = Integer.parseInt(voteValue);
        //get session id
        String sessionId = request.getRequestedSessionId();
        //record the vote
        try {
            pollManager.vote(sessionId, voteValueAsInt);
            //return
            response.sendRedirect(request.getContextPath() + "/vote.jsp");
        } catch (PollException e) {
            request.setAttribute("error", e);
            System.out.println(e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
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
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }
    }
}
