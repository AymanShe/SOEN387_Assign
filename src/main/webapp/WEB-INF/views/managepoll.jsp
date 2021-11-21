<%@ page import="com.soen387.model.Poll" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Zander
  Date: 13-Nov-21
  Time: 8:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="ManagedPoll" class="com.soen387.model.Poll" scope="request"/>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Title</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
Poll ID: <%= ManagedPoll.getPollId() %> <br/>
Poll Question: <%= ManagedPoll.getQuestion() %> <br/>
Poll Status: <%= ManagedPoll.getStatus() %> <br/>
Release date: <%= ManagedPoll.getReleaseDate() %> <br/>

<%
    ArrayList<String> allowedActions = (ArrayList<String>) request.getAttribute("allowedActions");
    for (String action : allowedActions) {
%>
<form method="post" action="<%= action %>">
    <input type="hidden" name="poll_id" value="<%= ManagedPoll.getPollId() %>">
    <input type="submit" value="<%= action %>">
</form>
<%
    }
%>
<button onclick='window.location = "<%= request.getContextPath() %>/Download?pollid=<%= ManagedPoll.getPollId() %>&format=" + document.getElementById("format").value;'>Download Poll</button>
<select name="format" id="format">Download File Format:
    <option value="txt">txt</option>
    <option value="xml">xml</option>
    <option value="json">json</option>
</select>

<%@ include file="sharedViews/footer.html" %>
</body>
</html>
