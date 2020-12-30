<%-- 
    Document   : listNotifications
    Created on : 28 nov. 2018, 00:11:27
    Author     : Kanfitine
--%>


<%@page import="cleanergy.webill.Notification"%>
<%@page import="cleanergy.webill.Variables"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>WeBill - Notifications</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
                <h1>List of all notifications</h1>
                    <%
                        ArrayList<Notification> notifications = (ArrayList) request.getAttribute("notifications");
                        if (notifications!=null){
                           %>
                            <table id="notificationtable" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <td>
                                            Date
                                        </td>
                                        <td>
                                            Message
                                        </td>
                                        <td>
                                            Action
                                        </td>
                                    </tr>
                                <thead>
                                <tbody>
                        <%
                        for (Notification notification : notifications) {
                            %>
                            
                            <tr>
                                        <td>
                                            <% if (notification.isStatus()){%>
                                            <b><%=notification.getTimeStamp()%></b>
                                           <%}else{%>
                                                    <%=notification.getTimeStamp()%>
                                            <%}%>
                                        </td>
                                        <td>
                                            <% String message = notification.getMessage(); message=message.substring(0,15);%>
                                           <% if (notification.isStatus()){%>
                                            <b> <%=message+"..."%></b>
                                           <%}else{%>
                                                     <%=message+"..."%>
                                            <%}%>
                                        </td>
                                        <td>
                                            <a href="viewNotification?notifId=<%=notification.getNoteId()%>" class="btn btn-success btn-cons">View</a>
                                        </td>
                                </tr>
                                                <%
                                       }
                                   %>
                                </tbody>
                            </table>
                            <%
                        }
                    %>
                <p></p>
            </div>

        </div>
        <div class="footer">
            <p>WeBill has been developed by 2018 Innovator course students</p>
        </div>
    </body>
</html>
<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#notificationtable').DataTable();
    });
</script>