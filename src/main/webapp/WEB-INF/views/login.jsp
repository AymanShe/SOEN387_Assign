<%--
  Created by IntelliJ IDEA.
  User: Zander
  Date: 14-Nov-21
  Time: 7:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Login</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
<form method="post" action="Login<%= request.getParameter("returnurl") == null ? "" : "?returnurl=" + request.getParameter("returnurl") %>">
    <label>Username</label>
    <input name="username" type="text">
    <label>Password</label>
    <input name="password" type="password">
    <input name="Login" type="submit">
</form>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>
