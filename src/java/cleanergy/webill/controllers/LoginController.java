package cleanergy.webill.controllers;

import cleanergy.webill.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Muhammad WANNOUS
 */
public class LoginController {

    public void process(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("post")) {
            String userEmail = request.getParameter("userEmail");
            String userSecret = request.getParameter("userSecret");
            ServletContext appContext = request.getServletContext();
            HashMap<String, String> errors = new HashMap<>();
            //Make sure that what is sent to us is a valid e-mail address xxx.yyy@zzz.aaa
            if (userEmail.matches("[a-z]{1,}[.a-z0-9]*@[a-z]{1,}.[a-z0-9]{1,}[.a-z0-9]*[a-z0-9]{1,}") 
            && userSecret.matches("[a-fA-F0-9]{1,}")){
                Connection dbConnection = (Connection) appContext.getAttribute("dbConnection");
                String matchingUserQuery = "select * from users where email='" + userEmail
                        + "' and secret='" + userSecret + "' and status = true;";
                ResultSet matchingUsers = dbConnection.createStatement().executeQuery(matchingUserQuery);
                if (matchingUsers.next()) {
                    User user = new User(matchingUsers.getInt("userId"),
                            matchingUsers.getString("gName"),
                            matchingUsers.getString("sName"),
                            matchingUsers.getString("role"),
                            matchingUsers.getString("email"),
                            matchingUsers.getBoolean("status"),
                            matchingUsers.getDate("dateOfChange"));
                    request.getSession().setAttribute("user", user);
                } else {
                    errors.put("No maching user!", "The email and secret you typed didn't match any "
                            + "record in the database");
                    request.setAttribute("errors", errors);

                }
            } else {
                errors.put("e-mail", "The e-mail address or password you typed is not valid. "
                        + "Please type an e-mail address in the format aa[.bb]@cc[.dd].ee!");
                request.setAttribute("errors", errors);
            }
            if (!errors.isEmpty()) {
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        }
    }
    
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.getSession().invalidate();
        response.sendRedirect("login");
    }
    
    public void loginpage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }
}
