<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Polling System - Poll Manager Entry</title>
</head>
<body class="container">
    <jsp:useBean id='poll' class='com.soen387.business.PollManager' scope="application"/>
    <br/>
    <h1 class="">Poll Manager</h1>
    <%
        String pollActions[] = poll.getAvailableActions();

        out.print("<br/>");
        for (String action: pollActions) {
            out.println("<form action=\"PollManager/" + action + "\" method=\"post\">");
            out.println("<input name=\"" + action + "Button\" type=\"submit\" class=\"btn btn-secondary\" value=\"" + action + " Poll\">");
            out.print("<br/>");
            out.print("<br/>");
            if (action.compareTo("Create") == 0 || action.compareTo("Update") == 0) {
                out.println("<div>");
                out.println("<label for=\"pollName\">Poll Title:</label>");
                out.println("<input type=\"text\" name=\"pollName\" required>");
                out.println("</div>");
                out.println("<div>");
                out.println("<label for=\"pollQuestion\">Poll Question:</label>");
                out.println("<input type=\"text\" name=\"pollQuestion\" required>");
                out.println("</div>");
                for (int i = 1; i <= 3; i++) {
                    out.println("<div>");
                    out.println("<label for=\"choiceName\">Choice #"+i+"- Text: </label>");
                    out.println("<input type=\"text\" name=\"choiceName\" required>");
                    out.println("<label for=\"choiceDesc\"> - Description: </label>");
                    out.println("<input type=\"text\" name=\"choiceDesc\">");
                    out.println("</div>");
                }
            }
            out.println("</form>");
        }
    %>
    <br/>
    <a href="<% out.print(request.getContextPath()); %>/WEB-INF/views/index.jspiews/index.jsp">Return to Home Page</a>
</body>
</html>