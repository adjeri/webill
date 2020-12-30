<%-- 
    Document   : allBills
    Created on : 28 nov. 2018, 12:48:48
    Author     : Kanfitine
--%>

<%@page import="cleanergy.webill.Bill"%>
<%@page import="cleanergy.webill.Variables"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>WeBill - Previous bills</title>
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
                
                <h1>List of all bills</h1>
                    <%
                        ArrayList<Bill> bills = (ArrayList) request.getAttribute("bills");
                        if (bills!=null){
                           %>
                            <table id="billtable" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <td>
                                            Date
                                        </td>
                                        <td>
                                            Customer
                                        </td>
                                        <td>
                                            Action
                                        </td>
                                    </tr>
                                <thead>
                                <tbody>
                        <%
                        for (Bill bill : bills) {
                            %>
                            
                                    <tr>
                                        <td>
                                           <%=bill.getDateOfPayment()%>
                                        </td>
                                        <td>
                                           <%=bill.getGivenName()+" ("+bill.getUserMail()+")"%> 
                                        </td>
                                        <td>
                                            <a class="btn btn-success btn-cons" href="download?filename=<%=bill.getBillFilePath()%>">Download</a>
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
        $('#billtable').DataTable();
    });
</script>

