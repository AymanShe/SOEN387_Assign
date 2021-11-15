<%@ page import="com.soen387.controller.Constants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Polling System - Landing Page</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
<div class="container">

    <jsp:useBean id='poll' class='com.soen387.business.PollManager' scope="application"/>
    <br/>
    <h1>Polling System</h1>
    <br/>
    <a href="PollManager">Poll Manager</a>
    <br/>
    <br/>
    <a href="Download">Download Poll</a>
    <br/>
    <br/>
    <a href="Vote">Submit Vote</a>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>