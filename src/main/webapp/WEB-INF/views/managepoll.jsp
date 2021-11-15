<%--
  Created by IntelliJ IDEA.
  User: Zander
  Date: 13-Nov-21
  Time: 8:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="ManagedPoll" class="com.soen387.model.Poll" scope="request" />
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

<%--TODO show and hide forms based on status--%>
<form method="post" action="run">
    <input type="hidden" name="poll_id" value="<%= ManagedPoll.getPollId() %>">
    <input type="submit" value="Run">
</form>
<form method="post" action="release">
    <input type="hidden" name="poll_id" value="<%= ManagedPoll.getPollId() %>">
    <input type="submit" value="Release">
</form>
<form method="post" action="unrelease">
    <input type="hidden" name="poll_id" value="<%= ManagedPoll.getPollId() %>">
    <input type="submit" value="Unrelease">
</form>
<form method="post" action="close">
    <input type="hidden" name="poll_id" value="<%= ManagedPoll.getPollId() %>">
    <input type="submit" value="Close">
</form>
<form method="post" action="delete">
    <input type="hidden" name="poll_id" value="<%= ManagedPoll.getPollId() %>">
    <input type="submit" value="Delete">
</form>



<%@ include file="sharedViews/footer.html" %>
</body>
</html>
