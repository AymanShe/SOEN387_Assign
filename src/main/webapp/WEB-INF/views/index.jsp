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


    <br/>
    <h1>Polling System</h1>
    <br/>
    <a href="PollManager">Poll Manager</a>
    <br/>
    <br/>
    <div class="card">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/ValidatePinPoll" method="post">
                <div class="form-group row">
                    <label for="pinId" class="col-md-2 col-form-label">Pin ID (optional)</label>
                    <div class="col-md-8">
                        <input id="pinId" type="text" class="form-control" name="pinId" placeholder="Enter Pin ID">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="pollId" class="col-md-2 col-form-label">Poll ID (required)</label>
                    <div class="col-md-8">
                        <input id="pollId" type="text" class="form-control" name="pollId" placeholder="Enter Poll ID" required>
                    </div>
                </div>
                <input type="submit" value="Access Poll to Vote" class="btn btn-primary float-right"/>
            </form>
        </div>
    </div>
    <br/>
    <br/>
    <a href="Download">Download Poll</a>
</div>
<%@ include file="sharedViews/footer.html" %>
</body>
</html>