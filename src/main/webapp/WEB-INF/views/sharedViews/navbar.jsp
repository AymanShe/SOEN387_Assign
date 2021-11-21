<%@ page import="com.soen387.controller.SessionManager" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="<%= request.getContextPath() %>">Poll System</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/Vote">Vote <span class="sr-only">(current)</span></a>
                </li>
                <%
                    if (SessionManager.isUserAuthenticated(request.getSession())) {
                %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Polls
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="<%= request.getContextPath() %>/Manage">Manage</a>
                        <a class="dropdown-item" href="<%= request.getContextPath() %>/Create">Create New</a>
                        <a class="dropdown-item" href="#">View</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item" href="#">Download</a>
                    </div>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
        <%
            if (SessionManager.isUserAuthenticated(request.getSession())) {
        %>
        <span class="navbar-text">Welcome <%= SessionManager.getAuthenticatedUserName(request.getSession()) %></span>
        <div class="d-flex justify-content-end">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/Logout">Logout</a>
        </div>
        <%
        } else {
        %>
        <div class="d-flex justify-content-end">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/Signup">Sign Up</a>&#160
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/Login">Login</a>
        </div>
        <%
            }
        %>


    </div>
</nav>