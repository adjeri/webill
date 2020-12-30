<%-- 
    Document   : newUpload
    Created on : 5 nov. 2018, 23:17:43
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
        <title>WeBill - Edit profile</title>
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
                    <%if((Boolean)request.getSession().getAttribute("submitValue")==true){%><li><a href="newUpload">New submission</a></li><% } %>
                    <li><a href="profile">Edit profile</a></li>
                    <li><a href="listBills">Previous bills</a></li>
                    <li><a href="listNotifications">Notifications</a></li>
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
            <c:if test = "${not empty sessionScope.user}">
                <form action="saveProfile" method="post">
                    <fieldset>
                        <legend>Update profile</legend>
                        <input type="hidden" id="userId" name="userId" value="${param.userId==null? sessionScope.user.userId :param.userId}" required>
                        <p>
                            <label for="gName">Given Name:</label>
                            <input type="text" id="gName" name="gName" value="${param.gName==null? sessionScope.user.gName :param.gName}" required>
                        </p>
                        <p>
                            <label for="sName">SurName: </label>
                            <input type="text" id="sName" name="sName" value="${param.sName==null? sessionScope.user.sName :param.sName}" required>
                        </p>
                        <p class="right-btn">
                            <input class="btn btn-success" type="submit" value="Submit">
                            <input class="btn btn-danger" type="button" value="Cancel" onclick="document.getElementById('back').click()">
                        </p>
                    </fieldset>
                </form>
                    <a id="back" href="dashboard" style="visibility: hidden">Dashboard</a>
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