<%-- 
    Document   : viewNotification
    Created on : 28 nov. 2018, 01:54:35
    Author     : Kanfitine
--%>

<%@page import="cleanergy.webill.Notification"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>Notification Details</title>
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
                    Notification notif = (Notification) request.getAttribute("notification");
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
                    <h1>Details</h1>
                    
                    <fieldset>
                        <legend>Content</legend>
                        <table cellpadding="5">
                            <tr>
                                <td>
                                    <label for="from">From: </label>
                                </td>
                                <td>
                                    Administrator
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="Date">Date: </label>
                                </td>
                                <td>
                                    <%=notif.getTimeStamp()%>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="Content"><b>Content:</b> </label>
                                </td>
                                <td>
                                    <%=notif.getMessage() %>
                                </td>
                            </tr>
                        </table> 
                                <br>
                                        <a class="btn btn-danger btn-cons pull-right" href="listNotifications">Back</a>
                    </fieldset>
            </div>

        </div>
        <div class="footer">
            <p>WeBill has been developed by 2018 Innovator course students</p>
        </div>
    </body>
</html>