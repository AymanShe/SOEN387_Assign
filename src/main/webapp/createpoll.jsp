<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Create a new Poll</title>
</head>
<body>
<div align="center">
    <h1>New Poll Form</h1>
    <form action="<%= request.getContextPath() %>/create" method="post">
        <table style="with: 80%">
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" /></td>
            </tr>
            <tr>
                <td>Question</td>
                <td><input type="text" name="question" /></td>
            </tr>
        </table>
        <input type="submit" value="Submit" />
    </form>
</div>
</body>
</html>