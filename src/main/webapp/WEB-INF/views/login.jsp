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
<div class="container">
    <%
        String errorMessage = request.getParameter("error");
        if (errorMessage != null) {
    %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <%= errorMessage %>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <%
        }
    %>
    <form method="post" action="Login<%= request.getParameter("returnurl") == null ? "" : "?returnurl=" + request.getParameter("returnurl") %>">
        <label>Username</label>
        <input name="username" type="text">
        <label>Password</label>
        <input name="password" type="password">
        <input name="Login" type="submit" class="btn btn-primary">
    </form>
    <a href="<%= request.getContextPath() %>/forget">I forgot my password.</a>
</div>
<%@ include file="sharedViews/footer.html" %>
<script>
    $( document ).ready(function() {
        history.pushState(null, "", location.href.split("?")[0]);
    });
</script>
</body>
</html>
