<%-- 
    Document   : consumerDashboard
    Created on : 26 oct. 2018, 12:41:56
    Author     : Kanfitine
--%>
<%@page import="cleanergy.webill.Notification"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.Image"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>WeBill - Consumer dashboard</title>
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
                <h1>Consumer dashboard</h1>
                 <%
                String newImageFilePath = (String) request.getSession().getAttribute("newUploadPath");
                if (newImageFilePath != null) {
            %>
            <fieldset>
                <legend>New Submission: Please Proceed</legend>
                <%
                        File imageFile = new File(newImageFilePath);
                        Image imageThumbnail = ImageIO.read(imageFile).getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                        BufferedImage buffTHumbnail = new BufferedImage(imageThumbnail.getWidth(null),
                                imageThumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);
                        buffTHumbnail.getGraphics().drawImage(imageThumbnail, 0, 0, null);
                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                        ImageIO.write(buffTHumbnail, "jpeg", outStream);
                        String base64THumbnail = Base64.getEncoder().encodeToString(outStream.toByteArray());
                        out.println("<img src=\"data:image/jpeg;base64," + base64THumbnail + "\"/>");
                %>
                <span>
                    <a class="btn btn-success btn-cons pull-right" id="imageCheckLink" href="checkConsumerImage">Check</a>
                </span>
            </fieldset>
                <% } %>
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
                        int numberofnotif = (int) request.getSession().getAttribute("notifNb");
                        if (numberofnotif > 0){%>
                        <p><b><a href="listNotifications">New notification(<%=numberofnotif%>)</a></b></p>
                        <%}%>
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
        $('#notiftable').DataTable();
    });
</script>