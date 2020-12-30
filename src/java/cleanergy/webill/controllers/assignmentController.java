/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.Assignment;
import cleanergy.webill.Meter;
import cleanergy.webill.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kanfitine
 */
public class assignmentController {
    public void addAssignement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
        String newmeterquery = "select * from meters where meters.meterId not in(select meterId from assignments) and meters.status=true;";
        ResultSet dbmeter = dbConnection.createStatement().executeQuery(newmeterquery);
        ArrayList<Meter> meter = new ArrayList<>();
        while (dbmeter.next()){
            meter.add(new Meter(dbmeter.getInt("meterId"),
            dbmeter.getString("qrCode"),
            dbmeter.getString("longitude"),
            dbmeter.getString("latitude"),
            dbmeter.getString("reading"),
            dbmeter.getString("address"),
            dbmeter.getBoolean("status")
            ));
        }
        request.setAttribute("meters", meter);
        
        ArrayList<User> user = new ArrayList<>();
        
            String allusersquery = "select * from users where role ='consumer'";
            ResultSet dbuser = dbConnection.createStatement().executeQuery(allusersquery);
            while (dbuser.next()){
                user.add(new User(dbuser.getInt("userId"),
                dbuser.getString("gName"),
                dbuser.getString("sName"),
                dbuser.getString("role"),
                dbuser.getString("email"),
                dbuser.getBoolean("status"),
                dbuser.getDate("dateOfChange")
                ));
            }
            request.setAttribute("users", user);
        request.getRequestDispatcher("WEB-INF/jsp/assignment.jsp").forward(request, response);
    }
    
    public void saveAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String startDate, userId, meterId;
        startDate = request.getParameter("startDate");
        userId = request.getParameter("userId");
        meterId = request.getParameter("meterId");
        HashMap<String, String> errors = new HashMap<>();
        if(startDate != null && userId != null && meterId !=null){
            Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
            String insertvariable = "insert into assignments(startDate, userId, meterId) values('"+startDate+"',"+userId+","+meterId+");";
            dbConnection.createStatement().executeUpdate(insertvariable);
            request.getSession().setAttribute("success", "New assignment added");
            response.sendRedirect("listAssignments");
        } else{
            if (startDate == null) errors.put("errors", "Please enter the start date");
            if (meterId == null) errors.put("errors", "Please select the meter");
            if (userId == null) errors.put("errors", "Please select the user");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/jsp/assignment.jsp").forward(request, response);
        }
    }
    
    public void listAssignments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
        String all = "select * from assignments, users, meters where meters.meterId = assignments.meterId and assignments.userId = users.userId;";
        ResultSet dball = dbConnection.createStatement().executeQuery(all);
        ArrayList<Assignment> assign = new ArrayList<>();
        while (dball.next()){
            assign.add(new Assignment(dball.getInt("userId"),
            dball.getInt("meterId"),
            dball.getInt("assignmentId"),
            dball.getString("gName"),
            dball.getString("email"),
            dball.getString("address")
            ));
        }
        request.setAttribute("assignments", assign);
        request.getRequestDispatcher("WEB-INF/jsp/listAssignments.jsp").forward(request, response);
    }
    
    public void deleteAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        String assignmentId = request.getParameter("assignmentId");
        if (assignmentId != null){
            String deletedassign = "delete from assignments where assignmentId="+assignmentId;
            dbConnection.createStatement().executeUpdate(deletedassign);
            request.getSession().setAttribute("success", "The assignment has been deleted");
            response.sendRedirect("listAssignments"); 
        }        
    }
}
