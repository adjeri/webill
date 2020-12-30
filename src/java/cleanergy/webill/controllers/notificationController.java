/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.Meter;
import cleanergy.webill.Notification;
import cleanergy.webill.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kanfitine
 */
public class notificationController {
    public void listNotifications(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        User connecteduser = (User) request.getSession().getAttribute("user");        
            ArrayList<Notification> notifications = new ArrayList<>();
            String queryForBills = "select n.notificationId, "
                    + "n.message,"
                    + "n.status,"
                    + "n.timeStamp,"
                    + "n.billId "
                    + " from notifications n, bills b, assignments a where "
                    + "n.billId = b.billId and "
                    + "b.assignmentId = a.assignmentId and "
                    + "a.userId ="+ connecteduser.getUserId()+" order by n.timeStamp DESC";
            ResultSet dbNote = dbConnection.createStatement().executeQuery(queryForBills);
            while (dbNote.next()){
                notifications.add(new Notification(dbNote.getInt("notificationId"),
                dbNote.getInt("billId"),
                dbNote.getString("message"),
                dbNote.getBoolean("status"),
                dbNote.getDate("timeStamp")));
            }
            request.setAttribute("notifications", notifications);
            request.getRequestDispatcher("WEB-INF/jsp/listNotifications.jsp").forward(request, response);
    }
    
    public void viewNotification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String notifId = request.getParameter("notifId");
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
         String query  = "select * from notifications where notificationId ="+notifId;
         ResultSet dbresult = dbConnection.createStatement().executeQuery(query);
         if (dbresult.next()){
             Notification notif = new Notification(dbresult.getInt("notificationId"),dbresult.getInt("billId"),
                     dbresult.getString("message"), dbresult.getBoolean("status"),dbresult.getDate("timeStamp"));
             request.setAttribute("notification", notif);
             query = "update notifications set status = false where notificationId="+notifId;
             dbConnection.createStatement().executeUpdate(query);
             request.getRequestDispatcher("WEB-INF/jsp/viewNotification.jsp").forward(request, response);
         }
    }
}
