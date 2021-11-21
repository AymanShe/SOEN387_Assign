<%@ page import="com.soen387.controller.Constants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Polling System - Error</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
<div class="container">
    <% out.print(((Exception)request.getAttribute("error")).toString()); %>
</h1>
<br/>
<a href="<% out.print(request.getContextPath()); %>">Return to home page</a>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>