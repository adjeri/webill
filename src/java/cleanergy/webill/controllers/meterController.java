/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.Meter;
import cleanergy.webill.utils.Validator;
import cleanergy.webill.SetTag;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;

/**
 *
 * @author Kanfitine
 */
public class meterController {
    public void addMeter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("WEB-INF/jsp/addMeter.jsp").forward(request, response);
    }
    
     public void listMeters(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        ArrayList<Meter> meter = new ArrayList<>();
        
            String allmetersquery = "select * from meters";
            ResultSet dbmeter = dbConnection.createStatement().executeQuery(allmetersquery);
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
            request.getRequestDispatcher("WEB-INF/jsp/listMeters.jsp").forward(request, response);
        
    }
     
      public void saveMeter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, WriterException, ImageReadException, ImageWriteException{
        String longitude, latitude, reading, address;
        boolean status ;
        longitude = request.getParameter("longitude");
        latitude = request.getParameter("latitude");
        reading = request.getParameter("reading");
        address = request.getParameter("address");
        if(request.getParameter("status")!=null && "true".equals(request.getParameter("status")))
            status = true;
        else
            status = false;
          Validator validator = new Validator();
          HashMap<String, String> errors = new HashMap<>();
          validator.validate(longitude, "longitude", "The longitude is not valid","[0-9]{1,3}[. ][0-9]{1,}", errors);
          validator.validate(latitude, "latitude", "The latitude is not valid","[0-9]{1,3}[. ][0-9]{1,}", errors);
          validator.validate(reading, "reading", "The reading is not valid","[0-9]{1,}[.0-9]{1,}", errors);
          if (address.isEmpty()) errors.put("Address", "Please enter the address");
          if(errors.isEmpty()){
              String findMeter = "select * from meters where longitude ="+longitude+" and latitude = "+latitude+";";
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet matchingMeters = dbConnection.createStatement().executeQuery(findMeter);
              if(matchingMeters.next()){
                  errors.put("Meter","This meter is already used in the system");
                  request.setAttribute("errors", errors);
                  request.getRequestDispatcher("WEB-INF/jsp/addMeter.jsp").forward(request, response);
              } else{
                  String createUserInDb = "insert into meters(longitude, latitude, reading, address,status) values("
                          + "'"+longitude+"',"
                          + "'"+latitude+"',"
                          + "'"+reading+"',"
                          + "'"+address+"',"
                          + status+");";
                  dbConnection.createStatement().executeUpdate(createUserInDb);
                    String newMeter = "select * from meters where longitude ='"+longitude+"' and latitude = '"+latitude+"';";
                    //Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
                ResultSet addMet = dbConnection.createStatement().executeQuery(newMeter);
                if(addMet.next()){
                    String meterId = addMet.getString("meterId");
                    String messageToCode = meterId+"_"+address;
                    String path = "C:/ImageFiles/QrCodes/";
                    if ((new ImageController()).generateQRCodeImage(messageToCode, 300, 300, meterId+"_",path)){
                        String fullpath = path+meterId+"_.jpeg";
                        String fullpath2 = path+meterId+".jpeg";
                        String updateMeter = "update meters set qrCode = '"+fullpath2+"' where meterId ="+meterId+";";
                        dbConnection.createStatement().executeUpdate(updateMeter);
                        File myfile = new File(fullpath);
                        File myfile2 = new File(fullpath2);
                        (new SetTag()).GeoTag(myfile, myfile2, Double.parseDouble(longitude), Double.parseDouble(latitude));
                        myfile.delete();
                        request.getSession().setAttribute("success", "A new meter has been added");
                        response.sendRedirect("listMeters"); 
                    }
                }
              }
                      
          }else{
              request.setAttribute("errors", errors);
              request.getRequestDispatcher("WEB-INF/jsp/addMeter.jsp").forward(request, response);
          }
      }
      
    public void meterEnableDisable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        String meterId = request.getParameter("meterId");
        Boolean status ;
        if (request.getParameter("meterStatus").equalsIgnoreCase("true"))
            status = true;
        else
            status = false;
        if (meterId != null){
            String updatemeter;
            if (status)
                updatemeter = "update meters set status=false where meterId="+meterId;
            else
                updatemeter = "update meters set status=true where meterId="+meterId;
            dbConnection.createStatement().executeUpdate(updatemeter);
            response.sendRedirect("listMeters"); 
        }        
    }
    
    public void deleteMeter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        String meterId = request.getParameter("meterId");
        if (meterId != null){
            String deleteNotification = "delete from notifications where billId in (select billId from bills where assignmentId in (select assignmentId from assignments where meterId ="+meterId+"))";
            dbConnection.createStatement().executeUpdate(deleteNotification);
            String deletebill = "delete from bills where assignmentId in (select assignmentId from assignments where meterId ="+meterId+")";
            dbConnection.createStatement().executeUpdate(deletebill);
            String deleteassignement = "delete from assignments where meterId="+meterId;
            dbConnection.createStatement().executeUpdate(deleteassignement);
            String deletedmeter = "delete from meters where meterId="+meterId;
            dbConnection.createStatement().executeUpdate(deletedmeter);
            File file = new File("c:\\ImageFiles\\QrCodes\\"+meterId+".jpeg");
            file.delete();
            request.getSession().setAttribute("success", "The assignment has been deleted");
            response.sendRedirect("listMeters"); 
        }        
    }
}
