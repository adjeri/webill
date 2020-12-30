<%-- 
    Document   : assignment
    Created on : 17 nov. 2018, 22:44:02
    Author     : Kanfitine
--%>


<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeBill - New assignment</title>
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
                    String success = (String) request.getSession().getAttribute("success");
                    if (success != null && !success.isEmpty())
                    {
                %>
                
                       <div class="success-message">
                       <%=success%>
                       <c:remove var="success"/>
                       </div>
                    <%  
                        }%>
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
        <form action="saveAssignment" method="post">
            <fieldset>
                <legend>New assignment</legend>
                <p>
                    <label for="startDate">Date: </label>
                    <input type="Date" id="startDate" name="startDate" value="${param.startDate==null? '':param.startDate}" required>
                </p>
                <p>
                    <label for="userId">User: </label>
                    <select id="userId" name="userId">
                        <c:forEach items="${requestScope.users}" var="user">
                                <option value="${user.userId}">${user.gName}</option>
                        </c:forEach>
                    </select>
                </p>
                <p>
                   <label for="meterId">Meter: </label>
                    <select id="meterId" name="meterId">
                        <c:forEach items="${requestScope.meters}" var="meter">
                                <option value="${meter.meterId}">${meter.address}</option>
                        </c:forEach>
                    </select>
                </p>
                <p class="right-btn">
                    <input class="btn btn-success" type="submit" value="Submit">
                    <input class="btn btn-danger" type="button" value="Cancel" onclick="document.getElementById('dashboardLink').click()">
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
