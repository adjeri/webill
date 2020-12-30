/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.Variables;
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

/**
 *
 * @author Kanfitine
 */
public class variableController {
    public void addVariable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("WEB-INF/jsp/vars.jsp").forward(request, response);
    }
    
    public void saveVariable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String variableName, variableValue;
        variableName = request.getParameter("variableName");
        variableValue = request.getParameter("variableValue");
        HashMap<String, String> errors = new HashMap<>();
        if(variableName != null && variableValue != null){
            String findvariable = "select * from variables where variableName ='"+variableName+"';";
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet foundvariable = dbConnection.createStatement().executeQuery(findvariable);
              if(foundvariable.next()){
                  errors.put("errors", "This variable is already defined in the system");
                  request.setAttribute("errors", errors);
                  request.getRequestDispatcher("WEB-INF/jsp/vars.jsp").forward(request, response);
              }
              else{
                  String insertvariable = "insert into variables(variableName, variableValue) values('"+variableName+"','"+variableValue+"');";
                  dbConnection.createStatement().executeUpdate(insertvariable);
                  request.getSession().setAttribute("success", "A new variable has been added");
                  response.sendRedirect("vars"); 
              }
        } else{
            if (variableName == null) errors.put("errors", "Please enter the variable name");
            if (variableValue == null) errors.put("errors", "Please enter the variable value");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/jsp/vars.jsp").forward(request, response);
        }
    }
    
    public void listVariables(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
         ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        ArrayList<Variables> variable = new ArrayList<>();
        
            String allvariablequery = "select * from variables";
            ResultSet dbvariable = dbConnection.createStatement().executeQuery(allvariablequery);
            while (dbvariable.next()){
                variable.add(new Variables(dbvariable.getInt("variableId"),
                dbvariable.getString("variableName"),
                dbvariable.getString("variableValue")
                ));
            }
            request.setAttribute("variables", variable);
            request.getRequestDispatcher("WEB-INF/jsp/listVariables.jsp").forward(request, response);
    }
    
    public void updateVariable(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
        String variableId = request.getParameter("variableId");
        HashMap<String, String> errors = new HashMap<>();
          if (variableId == null) errors.put("errors", "Invalid variable");
          request.setAttribute("errors", errors);
          if (errors.isEmpty()){
            ServletContext appContext = request.getServletContext();
            Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");        
            String allvariablequery = "select * from variables where variableId="+variableId;
            ResultSet dbvariable = dbConnection.createStatement().executeQuery(allvariablequery);
            if (dbvariable.next()){
               Variables variable = new Variables(dbvariable.getInt("variableId"),
                dbvariable.getString("variableName"),
                dbvariable.getString("variableValue")
                );
               request.getSession().setAttribute("variable", variable);
            request.getRequestDispatcher("WEB-INF/jsp/updateVars.jsp").forward(request, response);
            }
          }
    }
    
    public void saveVariableUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String variableName, variableValue;
        variableName = request.getParameter("variableName");
        variableValue = request.getParameter("variableValue");
         String variableId = request.getParameter("variableId");
        HashMap<String, String> errors = new HashMap<>();
        
        if(variableName != null && variableValue != null){
            String findvariable = "select * from variables where variableName ='"+variableName+"' and variableId <> "+variableId+";";
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet foundvariable = dbConnection.createStatement().executeQuery(findvariable);
              if(foundvariable.next()){
                  errors.put("errors", "This variable is already defined in the system");
                  request.setAttribute("errors", errors);
                  request.getRequestDispatcher("WEB-INF/jsp/updateVars.jsp").forward(request, response);
              }
              else{
                  String updatevariable = "update variables set variableName = '"+variableName+"', variableValue = '"+variableValue+"' where variableId="+variableId+";";
                  dbConnection.createStatement().executeUpdate(updatevariable);
                  request.getSession().setAttribute("success", "The variable has been updated");
                  listVariables(request, response);
              }
        } else{
            if (variableName == null) errors.put("errors", "Please enter the variable name");
            if (variableValue == null) errors.put("errors", "Please enter the variable value");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/jsp/updateVars.jsp").forward(request, response);
        }
    }
}
