<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Polling System - Landing Page</title>
</head>
<body class="container">
    <jsp:useBean id='poll' class='com.soen387.poll.PollManager' scope="application"/>
    <br/>
    <h1>Polling System</h1>
    <br/>
    <a href="PollManager">Poll Manager</a>
    <br/>
    <br/>
    <a href="Vote">Download Poll</a>
    <br/>
    <br/>
    <a href="vote.jsp">Submit Vote</a>
</body>
</html>