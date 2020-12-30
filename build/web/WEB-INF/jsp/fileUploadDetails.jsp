<%-- 
    Document   : fileUploadDetails
    Created on : 9 nov. 2018, 01:23:44
    Author     : Kanfitine
--%>

<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="css/styles.css">
        <title>File Details</title>
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
                <%
                        String qrcode = (String) request.getSession().getAttribute("QrCodeContent");
                    %>
                    <h1>Submission Details</h1>
                    <form action="confirmImageData" method="post">
                    <fieldset>
                        <legend>Please confirm the reading</legend>
                        <table>
                            <tr>
                                <td>
                                    <label for="qrcode">QR code: </label>
                                </td>
                                <td>
                                    <%if(session.getAttribute("QrCodeContent")!=null && session.getAttribute("QrCodeContent").toString().equalsIgnoreCase("valid")) {%>
                                        <input type="text" class="btn btn-success btn-cons" disabled name="qrcode" value="CHECKED" required>
                                    <%}
                                    else{%>
                                        <input type="text" class="btn btn-danger btn-cons" disabled name="qrcode" value="FAILED" required>
                                    <%    }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="longitude">Location: </label>
                                </td>
                                <td>
                                    <%if(session.getAttribute("longitude")!=null && session.getAttribute("longitude").toString().equalsIgnoreCase("valid") && session.getAttribute("latitude")!=null && session.getAttribute("latitude").toString().equalsIgnoreCase("valid")) {%>
                                        <input type="text" class="btn btn-success btn-cons" disabled name="longitude" value="CHECKED" required>
                                    <%}
                                    else{%>
                                        <input type="text" class="btn btn-danger btn-cons" disabled name="longitude" value="FAILED" required>
                                    <%    }
                                    %>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <label for="reading">Reading: </label>
                                </td>
                                <td>
                                    <input type="hidden" name="meterId" value="<%=request.getAttribute("meterId")%>">
                                    <input type="text" id="reading" name="reading" value="<%=session.getAttribute("reading")%>">
                                </td>
                            </tr>
                        </table> 
                                <br>
                                <input type="submit" value="Confirm" class="btn btn-success btn-cons pull-right">
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
