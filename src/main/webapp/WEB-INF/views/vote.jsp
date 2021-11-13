<%@ page import="com.soen387.model.Poll" %>
<%@ page import="com.soen387.model.Choice" %>
<%@ page import="java.util.Hashtable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<jsp:useBean id='poll' class='com.soen387.business.PollManager' scope="application"/>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Polling System - Poll Vote Page</title>


    <% if (poll.getPoll().getStatus() == Poll.PollStatus.valueOf("released")) {%>

    <!--Code taken from: https://developers.google.com/chart/interactive/docs/quick_start-->
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

        // Load the Visualization API and the corechart package.
        google.charts.load('current', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

            // Create the data table.
            let data = new google.visualization.DataTable();
            data.addColumn('string', 'Choice');
            data.addColumn('number', 'Amount');

            data.addRows([
                <%
                    Choice choices[] = poll.getPoll().getChoices();
                    Hashtable<Integer, Integer> results = poll.getPollResult();

                    for (int i = 0; i < choices.length; i++) {
                        if (i != 0) out.print("                ");
                        out.print("['" + choices[i].getText() + "', " + results.get(i) + "]");
                        if (i != choices.length - 1) out.print(",");
                        else out.println("");
                    }

                %>
            ]);

            // Set chart options
            var options = {'title':'Choices in the Poll',
                'width':400,
                'height':300};

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    </script>
    <% }%>

</head>

<body class="container">

    <br/>
    <h1>Poll</h1>

    <br/>
    <% if (poll.getPoll().getStatus() == Poll.PollStatus.valueOf("running")) {%>
    <h2>Poll: <%= poll.getPoll().getQuestion() %></h2>
    <form action="Vote" method="post" class="mb-3">
        <%
            Choice[] choices = poll.getPoll().getChoices();
            for(int i = 0; i< choices.length; i++){ %>
        <input type="radio" name="choice" value="<%= i %>"><%= choices[i].getText() %>(<%= choices[i].getDescription() %>) <br/>
        <% }%>
        <br/>
        <input type="submit" value="Submit" class="btn-primary">
    </form>
    <%} else {%>
    <h2>Poll is Not Currently Running</h2>
    <%}%>

    <hr>

    <% if (poll.getPoll().getStatus() == Poll.PollStatus.valueOf("released")) {%>
    <h2>Poll Google Chart</h2>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>

    <%} else {%>
    <h2>No Google chart as Poll Not Yet Released</h2>
    <%}%>
    <br/>
    <a href="<% out.print(request.getContextPath()); %>">Return to Home Page</a>
</body>
</html>
