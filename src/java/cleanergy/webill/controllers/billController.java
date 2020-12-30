/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.Bill;
import cleanergy.webill.Meter;
import cleanergy.webill.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.activation.MimetypesFileTypeMap;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author Kanfitine
 */
public class billController {
    public void checkOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String billId = request.getParameter("billId");
        HashMap<String, String> errors = new HashMap<>();
        if (billId == null) errors.put("errors", "Invalid bill info");
        if (errors.isEmpty()){
            String query = "select * from bills where billid = "+billId;
            Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet queryresult = dbConnection.createStatement().executeQuery(query);
              if(queryresult.next()){
                  Bill bill = new Bill(queryresult.getInt("billId"),
                          queryresult.getInt("assignmentId") , 
                          queryresult.getString("imageFilePath"),
                          queryresult.getString("billFilePath"),
                          queryresult.getString("previousReading"),
                          queryresult.getString("currentReading"),
                          queryresult.getBoolean("verifiedByConsumer"),
                          queryresult.getBoolean("verifiedByAdmin"),
                          queryresult.getString("paymentAmount"),
                          queryresult.getBoolean("paid"),
                          queryresult.getDate("dateOfPayment"),
                          queryresult.getInt("handlingAdminId"));
                  request.setAttribute("bill", bill);
              }
        }
        else 
            request.setAttribute("errors", errors);
        request.getRequestDispatcher("WEB-INF/jsp/checkout.jsp").forward(request, response);
    }
    
     public void generateBill(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, DocumentException{
        String billId = request.getParameter("billId");
        HashMap<String, String> errors = new HashMap<>();
        if (billId == null) errors.put("errors", "Invalid bill info");
        if (errors.isEmpty()){
            Boolean correctreading;
            if (request.getParameter("correctReading")!=null && request.getParameter("correctReading").equalsIgnoreCase("true"))
            CreatePdf(request,response,Integer.parseInt(billId));
            else{
                Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
                String notificationmessage = request.getParameter("message");
                String query = "select * from bills where billid = "+billId;
              ResultSet queryresult = dbConnection.createStatement().executeQuery(query);
              if(queryresult.next()){
                  Bill bill = new Bill(queryresult.getInt("billId"),
                          queryresult.getInt("assignmentId") , 
                          queryresult.getString("imageFilePath"),
                          queryresult.getString("billFilePath"),
                          queryresult.getString("previousReading"),
                          queryresult.getString("currentReading"),
                          queryresult.getBoolean("verifiedByConsumer"),
                          queryresult.getBoolean("verifiedByAdmin"),
                          queryresult.getString("paymentAmount"),
                          queryresult.getBoolean("paid"),
                          queryresult.getDate("dateOfPayment"),
                          queryresult.getInt("handlingAdminId"));
                  
                query = "select * from users where users.userId IN (select userId from assignments where assignmentId ="+bill.getAssignementId()+")";
                queryresult = dbConnection.createStatement().executeQuery(query);
                    if (queryresult.next()){
                        User user = new User(queryresult.getInt("userId"),
                        queryresult.getString("gName"),
                        queryresult.getString("sName"),
                        queryresult.getString("role"),
                        queryresult.getString("email"),
                        queryresult.getBoolean("status"),
                        queryresult.getDate("dateOfChange")
                        ); 
                    
                    User connecteduser = (User) request.getSession().getAttribute("user");
                    int handlingAdminId = connecteduser.getUserId();
                    
                    query = "update bills set handlingAdminId = "+handlingAdminId+" where billId = "+bill.getBillId();
                    dbConnection.createStatement().executeUpdate(query);
                    
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    
                    query = "insert into notifications(toId,fromId,message,billId,notificationDate,status) values"
                            + "("+user.getUserId()+","
                            + handlingAdminId+","
                            + "'"+notificationmessage+"',"
                            + bill.getBillId()+","
                            + "'"+dateFormat.format(date)+"',"
                            + "true)";
                    dbConnection.createStatement().executeUpdate(query);
                    
                    request.getSession().setAttribute("success", "A notification has been send to the customer");
                    response.sendRedirect("dashboard"); 
                   }
              }
            }
        }
     }

    public static void CreatePdf(HttpServletRequest request, HttpServletResponse response, int billId) throws SQLException, DocumentException, FileNotFoundException, IOException
    {	
        Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
        ///bill information
        String query = "select * from bills where billid = "+billId;
              ResultSet queryresult = dbConnection.createStatement().executeQuery(query);
              if(queryresult.next()){
                  Bill bill = new Bill(queryresult.getInt("billId"),
                          queryresult.getInt("assignmentId") , 
                          queryresult.getString("imageFilePath"),
                          queryresult.getString("billFilePath"),
                          queryresult.getString("previousReading"),
                          queryresult.getString("currentReading"),
                          queryresult.getBoolean("verifiedByConsumer"),
                          queryresult.getBoolean("verifiedByAdmin"),
                          queryresult.getString("paymentAmount"),
                          queryresult.getBoolean("paid"),
                          queryresult.getDate("dateOfPayment"),
                          queryresult.getInt("handlingAdminId"));
                  
                  //meter information
                    query = "select * from meters where meters.meterId IN (select meterId from assignments where assignmentId ="+bill.getAssignementId()+")";
                    queryresult = dbConnection.createStatement().executeQuery(query);
                     if (queryresult.next()){
                        Meter meter = new Meter(queryresult.getInt("meterId"),
                        queryresult.getString("qrCode"),
                        queryresult.getString("longitude"),
                        queryresult.getString("latitude"),
                        queryresult.getString("reading"),
                        queryresult.getString("address"),
                        queryresult.getBoolean("status")
                        ); 
                        
                    ///owner information
                        query = "select * from users where users.userId IN (select userId from assignments where assignmentId ="+bill.getAssignementId()+")";
                        queryresult = dbConnection.createStatement().executeQuery(query);
                        if (queryresult.next()){
                            User user = new User(queryresult.getInt("userId"),
                            queryresult.getString("gName"),
                            queryresult.getString("sName"),
                            queryresult.getString("role"),
                            queryresult.getString("email"),
                            queryresult.getBoolean("status"),
                            queryresult.getDate("dateOfChange")
                            );
                            
                            //// system variables
                        String allvariablequery = "select * from variables";
                        ResultSet variableresult = dbConnection.createStatement().executeQuery(allvariablequery);
                        int perKw = 0; double Tax = 0; int processingFees = 0;
                        double Discount = 0;
                        while(variableresult.next()){
                            switch (variableresult.getString("variableName")){
                                case "perKw":
                                    perKw = Integer.parseInt(variableresult.getString("variableValue"));
                                    break;
                                case "Tax":
                                    Tax = Double.parseDouble(variableresult.getString("variableValue"));
                                    Tax *= 100;
                                    break;
                                case "processingFees":
                                    processingFees = Integer.parseInt(variableresult.getString("variableValue"));
                                    break;
                                case "Discount":
                                    Discount = Double.parseDouble(variableresult.getString("variableValue"));
                                    Discount *=100;
                                    break;
                            }
                        }
                            String filelocation = "C:/imageFiles/"+user.getUserId()+"/bill_"+billId+".pdf";
                            Document document = new Document();
                            PdfWriter writer = PdfWriter.getInstance(document,
                            new FileOutputStream(filelocation));
                            String newline=System.getProperty("line.separator"); 
                            document.open();		
                            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                            Date date = new Date();
                            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(dateFormat.format(date)), 530, 800, 0);
                            PdfPTable table = new PdfPTable(1); 
                            table.setWidthPercentage(100);
                            Font font = new Font(FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.BLACK);
                            Font fonttitre = new Font(FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK);
                            Font font3 = new Font(FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
                            Paragraph praisonsociale = new Paragraph("Cleanergy", font);
                            praisonsociale.setAlignment(Element.ALIGN_CENTER);
                            Paragraph p1 = new Paragraph(" "); //empty
                            Paragraph p = new Paragraph("Bill", fonttitre);
                            p.setAlignment(Element.ALIGN_CENTER);
                            Paragraph p2 = new Paragraph(" "); //empty
                            document.add(praisonsociale);
                            document.add(p);
                            document.add(p1);
                            String mytext = newline+newline;
                            mytext +="Bill Number: "+bill.getBillId()+newline+newline;
                            mytext +="Customer: "+user.getgName()+newline;
                            mytext +="Adress: "+meter.getAddress()+newline+newline;
                            mytext +="Previous month reading: "+bill.getPreviousReading()+newline;
                            mytext +="Current month reading : "+bill.getCurrentReading()+newline;
                            double consumption = Double.parseDouble(bill.getCurrentReading())-Double.parseDouble(bill.getPreviousReading());
                            mytext +="Consumption: "+consumption+newline;
                            mytext +="Tax: "+Tax+"%"+newline;
                            mytext +="Processing fees: "+processingFees+newline;
                            mytext +="Discount: "+Discount+"%"+newline;
                            mytext +="Price per kW: "+perKw+newline+newline;
                            mytext +="Amount to pay: "+bill.getPaymentAmount()+newline+newline+newline;

                            PdfPCell bcell = new PdfPCell(new Paragraph(mytext,font3));
                            bcell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            table.addCell(bcell);

                            document.add(table);
                            document.close();
                            
                           User connecteduser = (User) request.getSession().getAttribute("user");
                            int handlingAdminId = connecteduser.getUserId();
                            
                            
                            String queryupdate = "update bills set paid = true, billFilePath = '"+filelocation+"', verifiedByAdmin = true, handlingAdminId="+handlingAdminId;
                            dbConnection.createStatement().executeUpdate(queryupdate);
                            
                           
                            request.getSession().setAttribute("success", "The bill has been generated succesfully");
                            response.sendRedirect("dashboard"); 
                       }
                    }
              }
    }
    
    public void previousBills(HttpServletRequest request, HttpServletResponse response) throws SQLException, DocumentException, FileNotFoundException, IOException, ServletException{
        User connecteduser = (User) request.getSession().getAttribute("user");
        Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
        int userId = connecteduser.getUserId();
        String query = "select * from bills where paid = true and assignmentId in (select assignmentId from assignments where userId="+userId+")";
        ResultSet queryresult = dbConnection.createStatement().executeQuery(query);
        ArrayList<Bill> bills = new ArrayList<>();
        while (queryresult.next()){
            bills.add(new Bill(queryresult.getInt("billId"),
            queryresult.getInt("assignmentId") , 
            queryresult.getString("imageFilePath"),
            queryresult.getString("billFilePath"),
            queryresult.getString("previousReading"),
            queryresult.getString("currentReading"),
            queryresult.getBoolean("verifiedByConsumer"),
            queryresult.getBoolean("verifiedByAdmin"),
            queryresult.getString("paymentAmount"),
            queryresult.getBoolean("paid"),
            queryresult.getDate("dateOfPayment"),
            queryresult.getInt("handlingAdminId")));
        }
        request.setAttribute("bills",bills);
        request.getRequestDispatcher("WEB-INF/jsp/previousBills.jsp").forward(request, response);
    }
    
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, IOException{
        String myfile = request.getParameter("filename");
        File file = new File(myfile);
        /*InputStream in = new URL(myfile).openStream();
        Files.copy(in, Paths.get(file.getName()), StandardCopyOption.REPLACE_EXISTING);*/
//       FileUtils.copyURLToFile(new URL(myfile),file);
        
        response.setContentType(new MimetypesFileTypeMap().getContentType(file));
        response.setContentLength((int)file.length());
        response.setHeader("content-disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

        InputStream is = new FileInputStream(file);
        FileCopyUtils.copy(is, response.getOutputStream());
    }
    
    public void allBills(HttpServletRequest request, HttpServletResponse response) throws SQLException, DocumentException, FileNotFoundException, IOException, ServletException{
        Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
        String query = "SELECT * FROM bills, users WHERE paid = true and assignmentId in (select assignmentId from assignments where assignments.userId = users.userId) order by billId desc";
        ResultSet queryresult = dbConnection.createStatement().executeQuery(query);
        ArrayList<Bill> bills = new ArrayList<>();
        while (queryresult.next()){
            bills.add(new Bill(queryresult.getInt("billId"),
            queryresult.getInt("assignmentId") , 
            queryresult.getString("imageFilePath"),
            queryresult.getString("billFilePath"),
            queryresult.getString("previousReading"),
            queryresult.getString("currentReading"),
            queryresult.getBoolean("verifiedByConsumer"),
            queryresult.getBoolean("verifiedByAdmin"),
            queryresult.getString("paymentAmount"),
            queryresult.getBoolean("paid"),
            queryresult.getDate("dateOfPayment"),
            queryresult.getInt("handlingAdminId"),
            queryresult.getString("email"),
            queryresult.getString("gName")));
        }
        request.setAttribute("bills",bills);
        request.getRequestDispatcher("WEB-INF/jsp/allBills.jsp").forward(request, response);
    }
}
