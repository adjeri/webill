/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.Bill;
import cleanergy.webill.Notification;
import cleanergy.webill.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kanfitine
 */
public class DashboardController {
    public void process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        HttpSession session = request.getSession();
        ServletContext appContext = request.getServletContext();
        User user = (User) session.getAttribute("user");
        Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        ArrayList<Bill> bills = new ArrayList<>();
        if (user.getRole().equalsIgnoreCase("admin")){
            String queryForBills = "select * from bills where verifiedByAdmin = false and handlingAdminId is null";
            ResultSet dbBill = dbConnection.createStatement().executeQuery(queryForBills);
            while (dbBill.next()){
                bills.add(new Bill(dbBill.getInt("billId"),
                dbBill.getInt("assignmentId"),
                dbBill.getString("imageFilePath"),
                dbBill.getString("billFilePath"),
                dbBill.getString("previousReading"),
                dbBill.getString("currentReading"),
                true,
                false,
                dbBill.getString("paymentAmount"),
                false,
                dbBill.getDate("dateOfPayment"),
                0));
            }
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("WEB-INF/jsp/adminDashboard.jsp").forward(request, response);
        } else{
            String queryForBills = "select count(n.notificationId) as total "
                    + " from notifications n, bills b, assignments a where "
                    + "n.status = true and "
                    + "n.billId = b.billId and "
                    + "b.assignmentId = a.assignmentId and "
                    + "a.userId ="+ user.getUserId();
            ResultSet dbNote = dbConnection.createStatement().executeQuery(queryForBills);
            int x = 0;
            if (dbNote.next())
                x = dbNote.getInt("total");
            request.getSession().setAttribute("notifNb", x);
            String allvariablequery = "select * from variables";
            ResultSet variableresult = dbConnection.createStatement().executeQuery(allvariablequery);
            int startDate = 0; int endDate = 0;
            while(variableresult.next()){
                switch (variableresult.getString("variableName")){
                    case "submissionStart":
                        startDate = Integer.parseInt(variableresult.getString("variableValue"));
                        break;
                    case "submissionEnd":
                        endDate = Integer.parseInt(variableresult.getString("variableValue"));
                        break;
                }
            }
            Calendar c = Calendar.getInstance();
            int today = c.get(Calendar.DAY_OF_MONTH);
            Boolean submitValue = false;
            if (today >= startDate && today <= endDate)
                submitValue = true;
            System.out.println(today+" -- submit value = "+true);
            request.getSession().setAttribute("submitValue", submitValue);
            request.getRequestDispatcher("WEB-INF/jsp/consumerDashboard.jsp").forward(request, response);
        }
    }
}
