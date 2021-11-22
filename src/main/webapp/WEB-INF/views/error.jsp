<%@ page import="com.soen387.controller.Constants" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Polling System - Error</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
<div class="container">
    <h3> Unexpected ERROR: <br/> <%= request.getAttribute("error") %> </h3>
</h1>
<br/>
<a href="<%= request.getContextPath() %>">Return to home page</a>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>