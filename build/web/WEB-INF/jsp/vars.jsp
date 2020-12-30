<%-- 
    Document   : vars
    Created on : 16 nov. 2018, 17:02:22
    Author     : Kanfitine
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeBill: Configuration variables</title>
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    </head>
    <body>
        <div class="header">
            <h1>Cleanergy&nbsp;&nbsp;<span style="color: deeppink; white-space: nowrap; font-size: small; vertical-align: bottom">Clean Energy for Good Life.</span></h1>
            <span><p class="pull-right">Welcome ${sessionScope.user.gName} (<a href="logout">logout</a>)</p></span>
        </div>
        <div class="row">

            <div class="col-4 col-s-5 menu">
                <ul>
                   <li><a href="dashboard">Dashboard</a></li>
                    <li><a href="addUser">Add a user</a></li>
                    <li><a href="listUsers">List users</a></li>
                    <li><a href="addMeter">Add a meter</a></li>
                    <li><a href="listMeters">List meters</a></li>
                    <li><a href="vars">Add a variable</a></li>
                    <li><a href="listVariables">List variables</a></li>
                    <li><a href="assignment">Add an assignment</a></li>
                    <li><a href="listAssignments">List assignments</a></li>
                    <li><a href="bills">Bills</a></li>
                </ul>
            </div>
                <%
                    HashMap<String, String> errors = (HashMap<String, String>) request.getAttribute("errors");
                    if (errors != null && !errors.isEmpty()) {
                        %>
                        <div class="error-message">
                       <%
                        for (HashMap.Entry<String, String> error : errors.entrySet()) {
                %>
                <!--<p><%=error.getKey()%></p>--> 
                <p><%=error.getValue()%></p>
                <%
                        } %>
                       </div> 
                    <% }%>
        <div class="col-8 col-s-7">
        <form action="saveVariable" method="post">
            <fieldset>
                <legend>New configuration variables</legend>
                <p>
                    <label for="variableName">Variable name:</label>
                    <input type="text" id="variableName" name="variableName" value="${param.variableName==null? '':param.variableName}" required>
                </p>
                <p>
                    <label for="variableValue">Variable value: </label>
                    <input type="text" id="variableValue" name="variableValue" value="${param.variableValue==null? '':param.variableValue}" required>
                </p>
                
                <p class="right-btn">
                    <input type="submit" value="Submit" class="btn btn-success btn-cons pull-right">
                    <input type="button" value="Cancel" class="btn btn-danger btn-cons pull-right" onclick="document.getElementById('dashboardLink').click()">
                </p>
            </fieldset>
        </form>
        <a id="dashboardLink" href="dashboard" style="visibility: hidden">Dashboard</a>
        </div>
        </div>
        <div class="footer">
            <div class="notes">
                <p>WeBill has been developed by 2018 Innovator course students</p>
            </div>
        </div>
    </body>
</html>