<%-- 
    Document   : listVariables
    Created on : 16 nov. 2018, 22:26:51
    Author     : Kanfitine
--%>


<%@page import="cleanergy.webill.Variables"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>WeBill - List of variables</title>
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
                <h1>List of all variables</h1>
                    <%
                        ArrayList<Variables> variables = (ArrayList) request.getAttribute("variables");
                        if (variables!=null){
                           %>
                            <table id="variabletable" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <td>
                                            Variable name
                                        </td>
                                        <td>
                                            Variable value
                                        </td>
                                        <td>
                                            Action
                                        </td>
                                    </tr>
                                <thead>
                                <tbody>
                        <%
                        for (Variables variable : variables) {
                            %>
                            
                                    <tr>
                                        <td>
                                           <%=variable.getVariableName()%>
                                        </td>
                                        <td>
                                           <%=variable.getVariableValue()%>
                                        </td>
                                        <td>
                                            <form action="updateVariable" method="post" style="display: inline-block">
                                                <input type="hidden" name="variableId" value="<%=variable.getVariableId()%>">
                                                <input type="submit" value="Edit" class="btn btn-success btn-cons">
                                            </form> 
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
        $('#variabletable').DataTable();
    });
</script>
