package cleanergy.webill;

import cleanergy.webill.controllers.DashboardController;
import cleanergy.webill.controllers.FileUploadController;
import cleanergy.webill.controllers.ImageController;
import cleanergy.webill.controllers.LoginController;
import cleanergy.webill.controllers.assignmentController;
import cleanergy.webill.controllers.billController;
import cleanergy.webill.controllers.meterController;
import cleanergy.webill.controllers.notificationController;
import cleanergy.webill.controllers.userController;
import cleanergy.webill.controllers.variableController;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.sanselan.ImageReadException;

/**
 *
 * @author Muhammad WANNOUS
 */
public class Dispatcher extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, NotFoundException, WriterException, ImageReadException, org.apache.commons.imaging.ImageReadException, ImageWriteException, DocumentException {
        String reqUri = request.getRequestURI().trim();
        String reqUrl = reqUri.substring(reqUri.lastIndexOf("/"));
        switch (reqUrl) {
            case "/login.action": {
            try {
                (new LoginController()).process(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }
            case "/dashboard": {
                 try {
                (new DashboardController()).process(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
            }
            case "/addUser":{
                new userController().addUser(request,response);
              break;  
            }
            case "/listUsers":{
                new userController().listUsers(request,response);
              break;  
            }
            case "/saveUser":{
                new userController().saveUser(request,response);
              break;  
            }
            
            case "/enabledisableUser":{
                new userController().EnableDisableUser(request,response);
              break;  
            }
            
            case "/userUpdate":{
                new userController().updateUser(request,response);
              break;  
            }
            
             case "/profile":{
                new userController().updateProfile(request,response);
              break;  
            }
            
              case "/saveProfile":{
                new userController().saveProfile(request,response);
              break;  
            }
              
              
             case "/saveUpdateUser":{
                new userController().saveUserUpdate(request,response);
              break;  
            }
            case "/newUpload": {
                (new FileUploadController()).viewForm(request, response);
                break;
            }
            case "/uploadFile": {
                (new FileUploadController()).upload(request, response);
                break;
            }
            
            case "/checkConsumerImage": {
                
                (new ImageController()).process(request, response);
                break;
            }
            
            case "/confirmImageData": {
                
                (new ImageController()).finalizeUpload(request, response);
                break;
            }
            
            case "/addMeter":{
                (new meterController ()).addMeter(request, response);
                break;
            }
            
            case "/saveMeter":{
                (new meterController ()).saveMeter(request, response);
                break;
            }
            
            case "/listMeters":{
                (new meterController ()).listMeters(request, response);
                break;
            }
            
            case "/deleteMeter":{
                (new meterController ()).deleteMeter(request, response);
                break;
            }
            
            case "/meterEnableDisable":{
                (new meterController ()).meterEnableDisable(request, response);
                break;
            }
            
            case "/vars":{
                (new variableController()).addVariable(request, response);
                break;
            }
            
            case "/saveVariable":{
                (new variableController()).saveVariable(request, response);
                break;
            }
            
            case "/listVariables":{
                (new variableController()).listVariables(request, response);
                break;
            }
            
            case "/updateVariable":{
                (new variableController()).updateVariable(request, response);
                break;
            }
            
            case "/saveVariableUpdate":{
                (new variableController()).saveVariableUpdate(request, response);
                break;
            }
            
            case "/assignment":{
                (new assignmentController()).addAssignement(request, response);
                break;
            }
            
            case "/saveAssignment":{
                (new assignmentController()).saveAssignment(request, response);
                break;
            }
            
            case "/listAssignments":{
                (new assignmentController()).listAssignments(request, response);
                break;
            }
            
            case "/deleteAssignment":{
                (new assignmentController()).deleteAssignment(request, response);
                break;
            }
                        
            case "/checkout":{
                (new billController()).checkOut(request, response);
                break;
            }
            
            case "/generateBill":{
                (new billController()).generateBill(request, response);
                break;
            }
            
            case "/listBills":{
                (new billController()).previousBills(request, response);
                break;
            }
            
            case "/download":{
                (new billController()).downloadFile(request,response);
                break;
            }
            
            case "/logout":{
                new LoginController().logout(request, response);
                break;
            }
            
             case "/login":{
                new LoginController().loginpage(request, response);
                break;
            }
             
             case "/listNotifications":{
                new notificationController().listNotifications(request, response);
                break;
            }
             
            case "/viewNotification":{
                new notificationController().viewNotification(request, response);
                break;
            }
            
            case "/bills":{
                new billController().allBills(request, response);
                break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | NotFoundException | WriterException | ImageReadException | org.apache.commons.imaging.ImageReadException | ImageWriteException | DocumentException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException | NotFoundException | WriterException | ImageReadException | org.apache.commons.imaging.ImageReadException | ImageWriteException | DocumentException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/webill?"
                    + "user=webill&password=itsme&useSSL=false&serverTimezone=Asia/Tokyo");
            getServletContext().setAttribute("dbConnection", dbConnection);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(Dispatcher.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
