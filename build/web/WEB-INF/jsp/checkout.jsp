<%-- 
    Document   : checkout
    Created on : 26 nov. 2018, 16:52:33
    Author     : Kanfitine
--%>


<%@page import="java.util.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.Image"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.File"%>
<%@page import="java.io.File"%>
<%@page import="cleanergy.webill.Bill"%>
<%@page import="cleanergy.webill.User"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WeBill - Checkout</title>
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
                 Bill bill = (Bill) request.getAttribute("bill");
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
                    <% }
                    if (bill!=null){%>
                        <form action="generateBill" method="post">
                            <fieldset>
                                <legend>Checkout</legend>
                                <input type="hidden" id="billId" name="billId" value="<%=bill.getBillId()%>" required>
                                <p>
                                    <label>Submission</label>
                                    <%
                                        File imageFile = new File(bill.getImageFilePath());
                                        Image imageThumbnail = ImageIO.read(imageFile).getScaledInstance(300, 200, BufferedImage.SCALE_SMOOTH);
                                        BufferedImage buffTHumbnail = new BufferedImage(imageThumbnail.getWidth(null),
                                                imageThumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);
                                        buffTHumbnail.getGraphics().drawImage(imageThumbnail, 0, 0, null);
                                        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                                        ImageIO.write(buffTHumbnail, "jpeg", outStream);
                                        String base64THumbnail = Base64.getEncoder().encodeToString(outStream.toByteArray());
                                        out.println("<img src=\"data:image/jpeg;base64," + base64THumbnail + "\"/>");
                                    %>
                                </p>
                                <p>
                                    <label for="currentReading">Input</label>
                                    <input disabled type="text" id="currentReading" name="currentReading" value="<%=bill.getCurrentReading()%>" required>
                                </p>
                                <p>
                                    <label for="correctReading">Correct reading</label>
                                    <input onclick="hideMessage()" type="checkbox" id="correctReading" name="correctReading" value="true" checked>
                                </p>
                                <div id="notif" style="display:none">
                                    <p>
                                    <label for="message">Message</label>
                                    <textarea id="message" name="message" rows="5"></textarea>
                                </p>
                                </div>
                                <p class="right-btn">
                                    <input class="btn btn-success" type="submit" value="Submit">
                                    <a class="btn btn-danger" href="dashboard">Back</a>
                                </p>
                            </fieldset>
                        </form> 
                    <%}
                    %>
        
        </div>
        </div>
        <div class="footer">
            <div class="notes">
                <p>WeBill has been developed by 2018 Innovator course students</p>
            </div>
        </div>
    </body>
</html>
<script type="text/javascript">
    function hideMessage(){
        var mycheckbox = document.getElementById("correctReading");
        var mydiv = document.getElementById("notif");
        var msg = document.getElementById("message");
        if (mycheckbox.checked) {
            mydiv.style.display = "none";
            msg.required = false; 
        } else {
            mydiv.style.display = "block";
            msg.required = true;
        }
    }
</script>