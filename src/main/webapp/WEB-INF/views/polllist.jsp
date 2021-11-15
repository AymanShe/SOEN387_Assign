<%--
  Created by IntelliJ IDEA.
  User: Zander
  Date: 14-Nov-21
  Time: 7:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="polls" type="java.util.List<com.soen387.model.Poll>" scope="request"/>
<html>
<head>
    <%@ include file="sharedViews/head.html" %>
    <title>Title</title>
</head>
<body>
<%@ include file="sharedViews/navbar.jsp" %>
<div class="container">
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col">Poll ID</th>
            <th scope="col">Poll Title</th>
            <th scope="col">Poll Status</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <c:forEach var="poll" items="${polls}">
            <tbody>
            <tr>
                <th scope="row">${poll.pollId}</th>
                <td>${poll.name}</td>
                <td>${poll.status}</td>
                <td align="center"><a href="<%= request.getContextPath() %>/Manage/${poll.pollId}">Manage</a></td>
            </tr>
            </tbody>
        </c:forEach>
    </table>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>
