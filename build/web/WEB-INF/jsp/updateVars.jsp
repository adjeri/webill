<%-- 
    Document   : updateVars
    Created on : 21 nov. 2018, 14:56:16
    Author     : Kanfitine
--%>

<%@page import="cleanergy.webill.User"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeBill - update a variable</title>
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
               
        <div class="col-8 col-s-7">
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
            <c:if test = "${not empty sessionScope.variable}">
                <form action="saveVariableUpdate" method="post">
            <fieldset>
                <legend>Update variable</legend>
                <input type="hidden" id="variableId" name="variableId" value="${param.variableId==null? sessionScope.variable.variableId :param.variableId}" required>
                <p>
                    <label for="variableName">Variable Name:</label>
                    <input type="text" id="variableName" name="variableName" value="${param.variableName==null? sessionScope.variable.variableName :param.variableName}" required>
                </p>
                <p>
                    <label for="variableValue">Variable value</label>
                    <input type="text" id="variableValue" name="variableValue" value="${param.variableValue==null? sessionScope.variable.variableValue :param.variableValue}" required>
                </p>
                <p class="right-btn">
                    <input class="btn btn-success" type="submit" value="Submit">
                    <input class="btn btn-danger" type="button" value="Cancel" onclick="document.getElementById('back').click()">
                </p>
            </fieldset>
        </form>
                    <a id="back" href="listVariables" style="visibility: hidden">Dashboard</a>
            </c:if>    
        
        
        </div>
        </div>
        <div class="footer">
            <div class="notes">
                <p>WeBill has been developed by 2018 Innovator course students</p>
            </div>
        </div>
    </body>
</html>