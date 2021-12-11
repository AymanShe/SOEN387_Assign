<%--
  Created by IntelliJ IDEA.
  User: Zander
  Date: 10-Dec-21
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Title</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
<div class="container">
    <form method="post" action="Change">
        <%
            if (SessionManager.isUserAuthenticated(request.getSession())) {
        %>
        <label>Old Password</label>
        <input name="oldPassword" type="password">
        <%
            }
        %>
        <label>New Password</label>
        <input name="password" type="password">
        <input name="Login" type="submit" class="btn btn-primary">
        <input name="username" type="hidden" value="<%= request.getAttribute("username") %>">
        <input name="token" type="hidden"  value="<%= request.getAttribute("token") %>">
    </form>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>
