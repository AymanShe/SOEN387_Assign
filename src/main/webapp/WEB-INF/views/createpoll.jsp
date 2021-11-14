<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Create a new Poll</title>
</head>
<body>
<%@ include file="sharedViews/header.jsp" %>
<div class="container">
    <h1>New Poll Form</h1>
    <div class="card">
        <div class="card-body">
            <form action="<%= request.getContextPath() %>/create" method="post">
                <div class="form-group row">
                    <label for="name" class="col-md-2 col-form-label">Poll Title</label>
                    <div class="col-md-8">
                        <input id="name" type="text" class="form-control" name="name" placeholder="Enter poll title" required>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="pollQuestion" class="col-md-2 col-form-label">Poll Question</label>
                    <div class="col-md-8">
                        <input id="pollQuestion" type="text" class="form-control" name="question" placeholder="Enter poll question" required>
                    </div>
                </div>

                <div class="form-group card">
                    <div class="card-header">
                        <label for="choiceName1" class="col-form-label">Choice #1</label>
                        <span style="font-size: 1.5rem; color: darkgrey;" class="float-right">
                            <i class="fas fa-trash-alt"></i>
                        </span>
                    </div>
                    <div class="form-group row card-body">
                        <label for="choiceName1" class="col-lg-1 col-md-3 col-form-label">Text</label>
                        <div class="col-lg-4 col-md-9">
                            <input id="choiceName1" type="text" name="choiceName1" class="form-control" placeholder="Enter choice" required>
                        </div>
                        <label for="choiceDesc1" class="col-lg-2 col-md-3 col-form-label">Description</label>
                        <div class="col-lg-5 col-md-9">
                            <input id="choiceDesc1" type="text" name="choiceDesc1" class="form-control" placeholder="Enter Description (optional)">
                        </div>
                    </div>
                </div>
                <div class="form-group card">
                    <div class="card-header">
                        <label for="choiceName2" class="col-form-label">Choice #2</label>
                        <span style="font-size: 1.5rem; color: darkgrey;" class="float-right">
                            <i class="fas fa-trash-alt"></i>
                        </span>
                    </div>
                    <div class="form-group row card-body">
                        <label for="choiceName2" class="col-lg-1 col-md-3 col-form-label">Text</label>
                        <div class="col-lg-4 col-md-9">
                            <input id="choiceName2" type="text" name="choiceName2" class="form-control" placeholder="Enter choice" required>
                        </div>
                        <label for="choiceDesc2" class="col-lg-2 col-md-3 col-form-label">Description</label>
                        <div class="col-lg-5 col-md-9">
                            <input id="choiceDesc2" type="text" name="choiceDesc2" class="form-control" placeholder="Enter Description (optional)">
                        </div>
                    </div>
                </div>
                <div class="form-group card">
                    <div class="card-header">
                        <label for="choiceName3" class="col-form-label">Choice #3</label>
                        <span style="font-size: 1.5rem; color: darkgrey;" class="float-right">
                            <i class="fas fa-trash-alt"></i>
                        </span>
                    </div>
                    <div class="form-group row card-body">
                        <label for="choiceName3" class="col-lg-1 col-md-3 col-form-label">Text</label>
                        <div class="col-lg-4 col-md-9">
                            <input id="choiceName3" type="text" name="choiceName3" class="form-control" placeholder="Enter choice" required>
                        </div>
                        <label for="choiceDesc3" class="col-lg-2 col-md-3 col-form-label">Description</label>
                        <div class="col-lg-5 col-md-9">
                            <input id="choiceDesc3" type="text" name="choiceDesc3" class="form-control" placeholder="Enter Description (optional)">
                        </div>
                    </div>
                </div>
                <h class="btn btn-secondary">
                    Add choice&nbsp;&nbsp;&nbsp;
                    <span style="color: darkgrey;" class="float-right"><i class="fas fa-plus"></i></span>
                </h>
                <input type="submit" value="Submit" class="btn btn-primary float-right"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>