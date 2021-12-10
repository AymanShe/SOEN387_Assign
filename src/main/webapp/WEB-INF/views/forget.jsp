<%--
  Created by IntelliJ IDEA.
  User: Zander
  Date: 10-Dec-21
  Time: 4:42 PM
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
    <form method="post" action="Forget">
        <label>Username</label>
        <input name="username" type="text" required>
        <input name="Login" type="submit">
    </form>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>
