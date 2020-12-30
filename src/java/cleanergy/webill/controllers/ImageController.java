/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cleanergy.webill.controllers;

import cleanergy.webill.User;
import com.google.zxing.BarcodeFormat;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.abs;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;

/**
 *
 * @author Kanfitine
 */
public class ImageController {
    
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NotFoundException, SQLException, ImageReadException {
        HttpSession session = request.getSession();
        if (session.getAttribute("newUploadPath") != null){
            String imageFilePath = (String) session.getAttribute("newUploadPath");
            Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
            int meterId = checkQrCode(request, response, imageFilePath, dbConnection);
            checkLocation(imageFilePath, request, meterId, dbConnection);
            processOcr(request, imageFilePath);
            request.setAttribute("meterId", meterId);
            request.getRequestDispatcher("WEB-INF/jsp/fileUploadDetails.jsp").forward(request, response);
        }
    }
    
    public int checkQrCode(HttpServletRequest request, HttpServletResponse response, String imageFilePath, Connection dbConnection) throws IOException, NotFoundException, ServletException, SQLException, ImageReadException{
        HashMap<String, String> errors = new HashMap<>();
        try{
            File imageFile = new File(imageFilePath);
            BufferedImage image = ImageIO.read(imageFile);
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader = new MultiFormatReader();
            String qrText = reader.decode(bitmap).getText();
            System.out.println(qrText);
            int meterId = Integer.parseInt(qrText.substring(0, qrText.indexOf("_")));
            if (meterId > 0){
                int userId = ((User) request.getSession().getAttribute("user")).getUserId();
            String query = "select * from assignments where userId=" + userId + " and "
                    + "meterId=" + meterId + ";";
            ResultSet queryResults = dbConnection.createStatement().executeQuery(query);
            if(queryResults.next()){
                request.getSession().setAttribute("assignmentId", queryResults.getString("assignmentId"));
                query = "select * from meters where meterId=" + meterId + ";";
                queryResults = dbConnection.createStatement().executeQuery(query);
                if(queryResults.next()){
                    File existingimageFile = new File(queryResults.getString("qrCode"));
                    image = ImageIO.read(existingimageFile);
                    pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
                    source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
                    bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    reader = new MultiFormatReader();
                    String existingqrText = reader.decode(bitmap).getText();
                    if(existingqrText.equals(qrText)){
                        request.getSession().setAttribute("QrCodeContent", "Valid");
                        return meterId;
                    }
                    else{
                        errors.put("QrCodeContent","Qr code error detected");
                        request.setAttribute("errors", errors);
                        request.getSession().setAttribute("newUploadPath", null);
                        request.getSession().setAttribute("QrCodeContent", null);
                        request.getSession().setAttribute("longitude", null);
                        request.getSession().setAttribute("latitude", null);
                        return 0;
                    }
                }
                else{
                        errors.put("QrCodeContent","Qr code error detected");
                        request.setAttribute("errors", errors);
                        request.getSession().setAttribute("newUploadPath", null);
                        request.getSession().setAttribute("QrCodeContent", null);
                        request.getSession().setAttribute("longitude", null);
                        request.getSession().setAttribute("latitude", null);
                        return 0;
                }
            }
            else{
                errors.put("QrCodeContent","Qr code error detected");
                request.setAttribute("errors", errors);
                request.getSession().setAttribute("newUploadPath", null);
                request.getSession().setAttribute("QrCodeContent", null);
                request.getSession().setAttribute("longitude", null);
                request.getSession().setAttribute("latitude", null);
                return 0;
            }
            }
            else{
                errors.put("QrCodeContent","Qr code error detected");
                request.setAttribute("errors", errors);
                request.getSession().setAttribute("newUploadPath", null);
                request.getSession().setAttribute("QrCodeContent", null);
                request.getSession().setAttribute("longitude", null);
                request.getSession().setAttribute("latitude", null);
                return 0;
            }
                
        } catch(NotFoundException | IOException exception){
            errors.put("QrCodeContent","Qr code error detected");
            request.setAttribute("errors", errors);
            System.out.println("No Qr code was detected");
            request.getSession().setAttribute("newUploadPath", null);
            request.getSession().setAttribute("QrCodeContent", null);
            request.getRequestDispatcher("WEB-INF/jsp/consumerDashboard.jsp").forward(request, response);
            return 0;
        }
    }
    
    public void checkLocation(String filePath, HttpServletRequest request, int meterId, Connection dbConnection) throws IOException, ImageReadException, SQLException{
       File imageFile = new File(filePath);
        Boolean noError = true;
        HashMap<String, String> errors = new HashMap<>();
        try {
            //The GPs data is saved as MetaData. We need to ceck if it exists.
            if ((Sanselan.getMetadata(imageFile) != null)
                    || (Sanselan.getMetadata(imageFile) instanceof IImageMetadata)) {
                //Check if we can convert it to JpegImageMetadata
                final IImageMetadata metadata = (IImageMetadata) Sanselan.getMetadata(imageFile);
                //Check if we can convert it to JpegImageMetadata
                if (metadata instanceof JpegImageMetadata) {
                    final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                    //Inside the MetaData, the GPS inforrmation is saved as EXIF data. Check if it exists.
                    if (jpegMetadata.getExif() != null) {
                        final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
                        //imageOrientation = exifMetadata.findField(ExifTagConstants.EXIF_TAG_ORIENTATION).getIntValue();
                        if (null != exifMetadata.getGPS()) {
                            final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                            if (null != gpsInfo) {
                                //Finally, we get to the GPS data.
                                //final String gpsDescription = gpsInfo.toString();
                                final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                                final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
                                //session.setAttribute("longitude", Double.toString(longitude));
                                //session.setAttribute("latitude", Double.toString(latitude));
                                System.out.println(longitude);
                                System.out.println(latitude);
                                String query = "select * from meters where meterId=" + meterId + ";";
                                ResultSet queryResults = dbConnection.createStatement().executeQuery(query);
                                    if(queryResults.next()){
                                        double existingLongitude = Double.parseDouble(queryResults.getString("longitude"));
                                        double existingLatitude = Double.parseDouble(queryResults.getString("latitude"));
                                        double validValue = 0.0001;
                                        if(abs(existingLongitude - longitude) <= validValue){
                                            request.getSession().setAttribute("longitude", "Valid");
                                        }
                                        else noError = false;
                                        if(abs(existingLatitude - latitude) <= validValue){
                                            request.getSession().setAttribute("latitude", "Valid");
                                        }
                                        else noError = false;
                                    }
                            }
                        } else {
                            noError = false;
                        }
                    } else {
                        noError = false;
                    }
                } else {
                    noError = false;
                }
            } else {
                noError = false;
            }
        } catch (ImageReadException ex) {
            //Logger.getLogger(ImageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!noError) {
            errors.put("longitude","Longitude and latitude error");
            request.setAttribute("errors", errors);
            request.getSession().setAttribute("longitude", "");
            request.getSession().setAttribute("latitude", "");
        }
    }
    
    public String processOcr(HttpServletRequest request, String filepath) {
        String[] command =
	    {
	        "cmd",
	    };
	    Process p;
		try {
                    Path currentRelativePath = Paths.get("");
                    String s = currentRelativePath.toAbsolutePath().toString();
                    System.out.println("Current relative path is: " + s);
                    String inputfile = filepath;
                    String outputfile = s+"\\temp";
                    p = Runtime.getRuntime().exec(command);
		
	    new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
	    new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
	    PrintWriter stdin = new PrintWriter(p.getOutputStream());
	    stdin.println("tesseract "+inputfile+" "+outputfile);
	    
	       
	    stdin.close();
	     p.waitFor();
             System.out.println();
             System.out.println();
             String mytext = ReadFile.read_a_file(outputfile+".txt");
             mytext = mytext.replaceAll("[^\\d.]", "");
             System.out.println(mytext);
             request.getSession().setAttribute("reading", mytext);
	     return mytext;
		} catch (Exception e) {
	 		e.printStackTrace();
                        request.getSession().setAttribute("reading", "");
                        return "";
		}
    }

    public boolean generateQRCodeImage(String text, int width, int height,String fileName,String paths)
            throws WriterException, IOException {
        try{
         // paths = "C:\\ImageFiles\\QrCodes\\";
        File folder = new File(paths);
        if(!folder.exists() || !folder.isDirectory()) folder.mkdirs();
        String filePath = paths+fileName+".jpeg";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
      
        
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "jpeg", path);   
        return true;
        } catch (IOException | WriterException e){
            return false;
        }
    }

    
    public void finalizeUpload(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        double currentReading = Double.parseDouble(request.getParameter("reading"));
        Connection dbConnection = (Connection) request.getServletContext().getAttribute("dbConnection");
        if (request.getSession().getAttribute("assignmentId")!= null && request.getSession().getAttribute("newUploadPath")!= null){
            String myfile = (String) request.getSession().getAttribute("newUploadPath");
            myfile = myfile.replace("\\", "/");
            int meterId = Integer.parseInt((String)request.getParameter("meterId"));
            //previous reading
            double previousReading = 0;
            String meterQuery = "select reading from meters where meterId="+meterId;
            ResultSet meterPreviousReading = dbConnection.createStatement().executeQuery(meterQuery);
            if (meterPreviousReading.next())
            previousReading = Double.parseDouble(meterPreviousReading.getString("reading"));
            //assignmentId
                int assignmentId = Integer.parseInt((String)request.getSession().getAttribute("assignmentId"));
            //variables
            String variableQuery = "select * from variables";
            ResultSet variableresult = dbConnection.createStatement().executeQuery(variableQuery);
            int perKw = 0; double Tax = 0; int processingFees = 0;
            double Discount = 0;
            while(variableresult.next()){
                switch (variableresult.getString("variableName")){
                    case "perKw":
                        perKw = Integer.parseInt(variableresult.getString("variableValue"));
                        break;
                    case "Tax":
                        Tax = Double.parseDouble(variableresult.getString("variableValue"));
                        break;
                    case "processingFees":
                        processingFees = Integer.parseInt(variableresult.getString("variableValue"));
                        break;
                    case "Discount":
                        Discount = Double.parseDouble(variableresult.getString("variableValue"));
                        break;
                }
            }
            //amount calculation
            double amountToPay = 0;
            double consumption = currentReading - previousReading;
            consumption = consumption * perKw;
            Tax = consumption * Tax;
            Discount = consumption * Discount;
            amountToPay = consumption + Tax + processingFees - Discount;
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            //System.out.println(dateFormat.format(date)); 
            String query = "insert into bills(imageFilePath,previousReading,currentReading,verifiedByConsumer,verifiedByAdmin,paymentAmount,paid,dateOfPayment,assignmentId)"
                    + " values('"+myfile+"','"+previousReading+"','"+currentReading+"',true,false,'"+amountToPay+"',false,'"+dateFormat.format(date)+"',"+assignmentId+")";
            dbConnection.createStatement().executeUpdate(query);
            String queryupdate = "update meters set reading = '"+currentReading+"' where meterId ="+meterId;
            dbConnection.createStatement().executeUpdate(queryupdate);
            request.getSession().setAttribute("newUploadPath", null);
            request.getSession().setAttribute("QrCodeContent", null);
            request.getSession().setAttribute("longitude", null);
            request.getSession().setAttribute("latitude", null);
            response.sendRedirect("dashboard");
        }
    } 
}
