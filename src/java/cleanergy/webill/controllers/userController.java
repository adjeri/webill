/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.User;
import cleanergy.webill.utils.Validator;
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
public class userController {
    public void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("WEB-INF/jsp/addUser.jsp").forward(request, response);
    }
    
     public void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        ServletContext appContext = request.getServletContext();
         Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
        ArrayList<User> user = new ArrayList<>();
        
            String allusersquery = "select * from users";
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
            request.getRequestDispatcher("WEB-INF/jsp/listUsers.jsp").forward(request, response);
    }
     
      public void saveUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String gName, sName, secret, email, role;
        boolean status ;
        gName = request.getParameter("gName");
        sName = request.getParameter("sName");
        email = request.getParameter("email");
        secret = request.getParameter("secret");
        role = request.getParameter("role");
        status = request.getParameter("status").equalsIgnoreCase("true")? true : false;
          Validator validator = new Validator();
          HashMap<String, String> errors = new HashMap<>();
          validator.validate(gName, "Given name", "Given name is not valid","[a-zA-Z]{1,}[. ]{0,}[a-zA-Z]{1,}", errors);
          validator.validate(sName, "Surname", "Surname is not valid", "[a-zA-Z]{1,}[. ]{0,}[a-zA-Z]{1,}", errors);
          validator.validate(email, "Email", "email is not valid", "[a-z]{1,}[.a-z0-9]*@[a-z]{1,}.[a-z0-9]{1,}[.a-z0-9]*[a-z0-9]{1,}", errors);
          validator.validate(role, "Role", "Role is not valid", "admin|consumer", errors);
          validator.validate(secret, "Secret", "The secret is not valid", "[a-fA-F0-9]{1,}", errors);
          if(errors.isEmpty()){
              String findUser = "select * from users where email ='"+email+"';";
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet matchingUsers = dbConnection.createStatement().executeQuery(findUser);
              if(matchingUsers.next()){
                  errors.put("Email","The email typed is already used in the system");
                  request.setAttribute("errors", errors);
                  request.getRequestDispatcher("WEB-INF/jsp/addUser.jsp").forward(request, response);
              } else{
                  String createUserInDb = "insert into users(gName, sName, email, role,secret,status) values("
                          + "'"+gName+"',"
                          + "'"+sName+"',"
                          + "'"+email+"',"
                          + "'"+role+"',"
                          + "'"+secret+"',"
                          + ""+status+");";
                  dbConnection.createStatement().executeUpdate(createUserInDb);
                  request.getSession().setAttribute("success", "A new user has been added");
                  response.sendRedirect("listUsers");
              }
                      
          }else{
              request.setAttribute("errors", errors);
              request.getRequestDispatcher("WEB-INF/jsp/addUser.jsp").forward(request, response);
          }
      }
      
      public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
          String userId;
        boolean status ;
        userId = request.getParameter("userId");
        
          HashMap<String, String> errors = new HashMap<>();
          if (userId == null) errors.put("errors", "Invalid user");
          request.setAttribute("errors", errors);
          if(errors.isEmpty()){
              String findUser = "select * from users where userId = "+userId+";";
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet matchingUsers = dbConnection.createStatement().executeQuery(findUser);
              if(!matchingUsers.next()){
                  errors.put("Email","The selected user has not been found");
                  request.setAttribute("errors", errors);
                  request.getRequestDispatcher("WEB-INF/jsp/updateUser.jsp").forward(request, response);
              } else{
                        User user = new User(matchingUsers.getInt("userId"),
                        matchingUsers.getString("gName"),
                        matchingUsers.getString("sName"),
                        matchingUsers.getString("role"),
                        matchingUsers.getString("email"),
                        matchingUsers.getBoolean("status"),
                        matchingUsers.getDate("dateOfChange")
                        );
                        request.getSession().setAttribute("usertoupdate", user);
                        request.getRequestDispatcher("WEB-INF/jsp/updateUser.jsp").forward(request, response);
                        System.out.println(user.getgName());
                    }  
          }else{
              request.setAttribute("errors", errors);
              request.getRequestDispatcher("WEB-INF/jsp/updateUser.jsp").forward(request, response);
          }
      }
      
      public void saveUserUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String gName, sName, email, role, userId;
        userId = request.getParameter("userId");
        gName = request.getParameter("gName");
        sName = request.getParameter("sName");
        email = request.getParameter("email");
        //secret = request.getParameter("secret");
        role = request.getParameter("role");
          Validator validator = new Validator();
          HashMap<String, String> errors = new HashMap<>();
          validator.validate(gName, "Given name", "Given name is not valid","[a-zA-Z]{1,}[. ]{0,}[a-zA-Z]{1,}", errors);
          validator.validate(sName, "Surname", "Surname is not valid", "[a-zA-Z]{1,}[. ]{0,}[a-zA-Z]{1,}", errors);
          validator.validate(email, "Email", "email is not valid", "[a-z]{1,}[.a-z0-9]*@[a-z]{1,}.[a-z0-9]{1,}[.a-z0-9]*[a-z0-9]{1,}", errors);
          validator.validate(role, "Role", "Role is not valid", "admin|consumer", errors);
        //  validator.validate(secret, "Secret", "The secret is not valid", "[a-fA-F0-9]{1,}", errors);
          if(errors.isEmpty()){
              String findUser = "select * from users where email ='"+email+"' and userId <> "+userId+";";
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              ResultSet matchingUsers = dbConnection.createStatement().executeQuery(findUser);
              if(matchingUsers.next()){
                  errors.put("Email","The email typed is already used in the system");
                  request.setAttribute("errors", errors);
                  request.getRequestDispatcher("WEB-INF/jsp/updateUser.jsp").forward(request, response);
              } else{
                  String updateUserInDb;
                    updateUserInDb = "update users set gName='"+gName+"',sName='"+sName+"',email='"+email+"', role='"+role+"' where userId="+userId+";";
                  dbConnection.createStatement().executeUpdate(updateUserInDb);
                  request.getSession().setAttribute("success", "The user information has been updated");
                  response.sendRedirect("listUsers");
              }
                      
          }else{
              request.setAttribute("errors", errors);
              request.getRequestDispatcher("WEB-INF/jsp/updateUser.jsp").forward(request, response);
          }
      }
      
      public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{        
          request.getRequestDispatcher("WEB-INF/jsp/editProfile.jsp").forward(request, response);
    }
      
      public void saveProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
        String gName, sName, userId;
        userId = request.getParameter("userId");
        gName = request.getParameter("gName");
        sName = request.getParameter("sName");
          Validator validator = new Validator();
          HashMap<String, String> errors = new HashMap<>();
          validator.validate(gName, "Given name", "Given name is not valid","[a-zA-Z]{1,}[. ]{0,}[a-zA-Z]{1,}", errors);
          validator.validate(sName, "Surname", "Surname is not valid", "[a-zA-Z]{1,}[. ]{0,}[a-zA-Z]{1,}", errors);
         
          if(errors.isEmpty()){
              Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
              
                  String updateUserInDb = "update users set gName='"+gName+"',sName='"+sName+"' where userId="+userId+";";
                  dbConnection.createStatement().executeUpdate(updateUserInDb);
                  request.getSession().setAttribute("success", "Your profile has been updated");
                String userquery = "select * from users where userId="+userId;
                ResultSet dbuser = dbConnection.createStatement().executeQuery(userquery);
                
                if (dbuser.next()){
                   User user= new User(dbuser.getInt("userId"),
                    dbuser.getString("gName"),
                    dbuser.getString("sName"),
                    dbuser.getString("role"),
                    dbuser.getString("email"),
                    dbuser.getBoolean("status"),
                    dbuser.getDate("dateOfChange")
                    );
                   request.getSession().setAttribute("user", user);
                   request.getSession().setAttribute("success", "The user information has been updated");
                  response.sendRedirect("dashboard");
                }                      
          }else{
              request.setAttribute("errors", errors);
              request.getRequestDispatcher("WEB-INF/jsp/editProfile.jsp").forward(request, response);
          }     
    }
      
      public void EnableDisableUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
            ServletContext appContext = request.getServletContext();
             Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
            String userId = request.getParameter("userId");
            Boolean status ;
            if (request.getParameter("status").equalsIgnoreCase("true"))
                status = true;
            else
                status = false;
            if (userId != null){
                String updateUser;
                if (status)
                    updateUser = "update users set status=false where userId="+userId;
                else
                    updateUser = "update users set status=true where userId="+userId;
                dbConnection.createStatement().executeUpdate(updateUser);
                response.sendRedirect("listUsers");
            }
      }
}
