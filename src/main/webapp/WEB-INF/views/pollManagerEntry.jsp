<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Polling System - Poll Manager Entry</title>
</head>
<body class="container">
<br/>
    <form action="PollManager/confirmPassword" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="pass">Password:</label>
            <input type="password" id="pass" name="password" required>
        </div>
        <input name="passwordButton" type="submit" value="Enter" class="btn-primary">
    </form>
</h1>
<br/>
    <a href="<% out.print(request.getContextPath()); %>">Return to home page</a>
</body>
</html>