<%-- 
    Document   : addMeter
    Created on : 13 nov. 2018, 11:35:29
    Author     : Kanfitine
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>New meter</title>
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
                    String success = (String) request.getAttribute("success");
                    if (success != null && !success.isEmpty())
                    {
                %>
                        <div class="success-message">
                       <%=success%>
                       </div>
                    <%  
                        }
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
                    <form action="saveMeter" method="post">
                    <fieldset>
                        <legend>New meter</legend>
                        <table>
                            <tr>
                                <td>
                                    Longitude:
                                </td>
                                <td>
                                    <input type="number" step="any" id="longitude" name="longitude" value="${param.longitude==null? '':param.longitude}" required>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Latitude:
                                </td>
                                <td>
                                    <input type="number" step="any" id="latitude" name="latitude" value="${param.latitude==null? '':param.latitude}" required>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Reading: 
                                </td>
                                <td>
                                    <input type="number" step="any" id="reading" name="reading" value="${param.reading==null? '':param.reading}" required>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Address:
                                </td>
                                <td>
                                    <input type="text" id="address" name="address" value="${param.address==null? '':param.address}" required>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Status:
                                </td>
                                <td>
                                    <input type="checkbox" id="status" name="status" value="true" ${param.status==true? 'checked':''}>Active
                                </td>
                            </tr>
                        </table> 
                                <br>
                         <input type="submit" value="Submit" class="btn btn-success btn-cons pull-right">
                            <a class="btn btn-danger btn-cons pull-right" id="imageCheckLink" href="dashboard">Back</a>
                    </fieldset>
                    </form>
            </div>

        </div>
        <div class="footer">
            <p>WeBill has been developed by 2018 Innovator course students</p>
        </div>
    </body>
</html>