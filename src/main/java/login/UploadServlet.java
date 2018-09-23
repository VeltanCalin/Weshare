package login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


@WebServlet(name = "UploadServlet", urlPatterns = { "/UploadServlet" }, loadOnStartup = 1) // loads when tomcat starts

    @MultipartConfig(fileSizeThreshold = 6*1024*1024, // 6 MB. this is the limit on temp mem storage  vs file /tmp storage
            maxFileSize = 1000*1024*1024, // 10 MB, this is the max of a single uploaded file
            maxRequestSize = 2000*1024*1024 // 20 MB , this is the total max size for a request
    )
    public class UploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "posters";

    String end = getDate()+ "_" + getTime();

    private  final static String getDate(  )   {
        DateFormat df = new SimpleDateFormat( "yyyy-MM-dd" ) ;
        df.setTimeZone( TimeZone.getTimeZone( "PST" )  ) ;
        return ( df.format( new Date(  )  )  ) ;
    }
    private  final static String getTime(  )   {
        DateFormat df = new SimpleDateFormat( "hh-mm-ss" ) ;
        df.setTimeZone( TimeZone.getTimeZone( "GMT+2:00" )  ) ;


        return ( df.format( new Date(  )  )  ) ;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // optional, in case we write somemthing on output
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // obtain a path where to store files, we can also use absolute paths it depends on the  permissions
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        // creates upload folder if it does not exists
        File uploadFolder = new File(uploadFilePath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String fileName = null;
        String nameFileToSaveInDB = null;
        UploadDetail details = null;
        List<UploadDetail> fileList = new ArrayList<UploadDetail>();
        // write all the files in our upload folder

        for (Part part : request.getParts()) {


            if (part != null && part.getSize() > 0) {
                fileName = part.getSubmittedFileName();
                String contentType = part.getContentType();


//                    // server side validation: allows files excepted excutable files to be uploaded, feel free to change this behaviour
                if (contentType != null && !contentType.equalsIgnoreCase(".exe") &&
                        !contentType.equalsIgnoreCase(".sh")) {


                    //make each file name unique
                    // UploadServlet d = new UploadServlet();
                    fileName = end + " " + fileName;
                    details = new UploadDetail();
                    details.setFileName(fileName);
                    details.setFileSize(part.getSize() / 1024);
                   // details.setUsername();

                    // actual write on the disk
                    try {
                        part.write(uploadFilePath + File.separator + fileName);
                        details.setUploadStatus("Success");
                    } catch (IOException ioObj) {
                        details.setUploadStatus("Failure : "+ ioObj.getMessage());
                    }
                    fileList.add(details);

                    if (fileName != null)
                        nameFileToSaveInDB = fileName;

//                    // feel free to remove the following, eventually in the end (after for ends) redirect to some url
//                    PrintWriter writer = response.getWriter();
//                    writer.append("File successfully uploaded to: "
//                            + uploadFolder.getAbsolutePath()
//                            + File.separator
//                           + fileName
//                            + "<br>\r\n");
                    }


                    else {

                        System.out.println("File format is not acceptable");
                    }

                }

            }


            String name = request.getParameter("name");


            System.out.println("name = " + name + " filename = " + nameFileToSaveInDB);


            // apelez db-ul si scriu cele 3 valori in db

            DBOper d = new DBOper();

            d.insertFile(name, nameFileToSaveInDB);

           // response.sendRedirect("fileupload.jsp");
        request.setAttribute("uploadedFiles", fileList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/fileuploadResponse.jsp");
        dispatcher.forward(request, response);


        }
    }




