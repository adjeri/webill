<%-- 
    Document   : addUser
    Created on : Oct 30, 2018, 12:32:50 PM
    Author     : Muhammad WANNOUS
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeBill: adding a new user</title>
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
        <form action="saveUser" method="post">
            <fieldset>
                <legend>New User</legend>
                <p>
                    <label for="gName">Given Name:</label>
                    <input type="text" id="gName" name="gName" value="${param.gName==null? '':param.gName}" required>
                </p>
                <p>
                    <label for="sName">Sur Name</label>
                    <input type="text" id="sName" name="sName" value="${param.sName==null? '':param.sName}" required>
                </p>
                <p>
                    <label for="email">E-mail</label>
                    <input type="text" id="email" name="email" value="${param.email==null? '':param.email}" required>
                </p>
                <p>
                    <label for="secret">Secret</label>
                    <input type="password" id="secret" name="secret" value="${param.secret==null? '':param.secret}" required>
                </p>
                <p>
                    <label for="status">Status</label>
                    <input type="checkbox" id="status" name="status" value="true" ${param.status==true? 'checked':''}>Active
                </p>
                <p>
                    <label for="role">User Role</label>
                    <select id="role" name="role">
                        <option value="admin" ${param.role.equals("admin")? 'selected':''}>Administrator</option>
                        <option value="consumer" ${param.role.equals("consumer")? 'selected':''}>consumer</option>
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
