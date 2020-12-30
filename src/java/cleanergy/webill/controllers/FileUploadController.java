package cleanergy.webill.controllers;

import cleanergy.webill.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Muhammad WANNOUS
 */
public class FileUploadController {

    public void viewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/fileUploadForm.jsp").forward(request, response);
    }

    public void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        
        String path = "C:\\ImageFiles\\" + ((User) request.getSession().getAttribute("user")).getUserId();
        Part filePart = request.getPart("file");
        String fileName = month+"-"+year+".jpg";
        File folder = new File(path);
        if(!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        OutputStream out = null;
        InputStream filecontent = null;
        try {
            String fullPath = path+"\\"+fileName;
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            request.getSession().setAttribute("newUploadPath", fullPath);
        } catch (FileNotFoundException fne) {
            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, fne);
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
        response.sendRedirect("dashboard");
    }
}
