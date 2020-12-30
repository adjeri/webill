<%-- 
    Document   : listMeters
    Created on : 16 nov. 2018, 10:06:18
    Author     : Kanfitine
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="cleanergy.webill.Meter"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.Image"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>WeBill - List of meters</title>
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
                <h1>List of all meters</h1>
                    <%
                        ArrayList<Meter> meters = (ArrayList) request.getAttribute("meters");
                        if (meters!=null){
                           %>
                            <table id="metertable" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <td>
                                            QRCode
                                        </td>
                                        <td>
                                            Address
                                        </td>
                                        <td>
                                            Reading
                                        </td>
                                        <td>
                                            Status
                                        </td>
                                        <td>
                                            Action
                                        </td>
                                    </tr>
                                <thead>
                                <tbody>
                        <%
                        for (Meter meter : meters) {
                            %>
                            
                                    <tr>
                                        <td style="text-align: center;">    
                                        <%
                                        if (meter.getQrCode()!=null){
                                                String imagepath = meter.getQrCode();
                                            File imageFile = new File(imagepath);
                                            Image imageThumbnail = ImageIO.read(imageFile).getScaledInstance(50, 50, BufferedImage.SCALE_SMOOTH);
                                            BufferedImage buffTHumbnail = new BufferedImage(imageThumbnail.getWidth(null),
                                                    imageThumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);
                                            buffTHumbnail.getGraphics().drawImage(imageThumbnail, 0, 0, null);
                                            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                                            ImageIO.write(buffTHumbnail, "jpeg", outStream);
                                            String base64THumbnail = Base64.getEncoder().encodeToString(outStream.toByteArray());
                                            out.println("<img src=\"data:image/jpeg;base64," + base64THumbnail + "\"/>");
                                        }
                                        %>
                                    </td>
                                    <td>
                                       <%=meter.getAddress()%>
                                    </td>
                                    <td>
                                        <%=meter.getReading()%>
                                    </td>
                                    <td>
                                        <% if(meter.isStatus() == true) {%> Activated <%} else {%>Deactivated <%}%>
                                    </td>
                                    <td>
                                        <form action="meterEnableDisable" method="post" style="display: inline-block">
                                                <input type="hidden" name="meterId" value="<%=meter.getMeterId()%>">
                                                <input type="hidden" name="meterStatus" value="<%=meter.isStatus()%>">
                                                <% if(meter.isStatus()==true) {%> <input type="submit" class="btn btn-warning" value="Disable"><%} else {%><input type="submit" class="btn btn-info" value="Enable" ><%}%>
                                        </form>
                                        <form action="deleteMeter" method="post" style="display: inline-block">
                                                <input type="hidden" name="meterId" value="<%=meter.getMeterId()%>">
                                                <input class="btn btn-danger" type="submit" value="Delete">
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
        $('#metertable').DataTable();
    });
</script>