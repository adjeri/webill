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
        <title>WeBill: uploading a new file</title>
        <link rel="stylesheet" href="css/styles.css" type="text/css"/>
    </head>
    <body>
        <div class="header">
            <h1>Cleanergy&nbsp;&nbsp;<span style="color: deeppink; white-space: nowrap; font-size: small; vertical-align: bottom">Clean Energy for Good Life.</span></h1>
            <span><p class="pull-right">Welcome ${sessionScope.user.gName} (<a href="logout">logout</a>)</p></span>
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
        <div class="col-3 col-s-3"></div>
        <form action="uploadFile" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Select File</legend>
                <p>
                    <label for="email">Pick a file</label>
                    <input type="file" id="fileId" name="file">
                </p>
                <p class="right-btn">
                    <input class="btn btn-success btn-cons" type="submit" value="Submit">
                    <input class="btn btn-danger btn-cons" type="button" value="Cancel" onclick="document.getElementById('dashboardLink').click()">
                </p>
            </fieldset>
        </form>
        <a id="dashboardLink" href="dashboard" style="visibility: hidden">Dashboard</a>
        <div class="footer">
            <div class="notes">
                <p>WeBill has been developed by 2018 Innovator course students</p>
            </div>
        </div>
    </body>
</html>
