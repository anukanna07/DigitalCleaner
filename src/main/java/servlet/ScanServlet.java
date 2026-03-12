package servlet;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import utils.ImageAnalyzer;
import utils.FileInfo;

@WebServlet("/ScanServlet")

@MultipartConfig(

location="/tmp",

fileSizeThreshold=1024*1024*2,

maxFileSize=1024*1024*50,

maxRequestSize=1024*1024*100

)

public class ScanServlet extends HttpServlet{

protected void doPost(

HttpServletRequest request,

HttpServletResponse response)

throws ServletException, IOException{


/* SESSION CHECK */

HttpSession session=
request.getSession(false);

if(session==null ||
session.getAttribute("loggedIn")==null){

response.sendRedirect("login.html");

return;

}


/* VERIFY MULTIPART */

String contentType=
request.getContentType();

if(contentType==null ||
!contentType.toLowerCase()
.startsWith("multipart/")){

response.getWriter()
.println("Invalid upload request");

return;

}


/* GET FILE PARTS */

Collection<Part> parts=
request.getParts();

List<File> files=
new ArrayList<>();


/* TEMP DIRECTORY */

String uploadPath="/tmp/uploads";

File uploadDir=
new File(uploadPath);

if(!uploadDir.exists()){

uploadDir.mkdirs();

}


/* SAVE FILES */

for(Part part:parts){

if(!"screenshots"
.equals(part.getName()))
continue;

String fileName=
part.getSubmittedFileName();

if(fileName==null ||
fileName.isEmpty())
continue;

fileName=
new File(fileName)
.getName();


if(!(fileName
.toLowerCase()
.endsWith(".png") ||

fileName
.toLowerCase()
.endsWith(".jpg") ||

fileName
.toLowerCase()
.endsWith(".jpeg")))
continue;


/* SAVE */

/* SAVE */

File file=new File(uploadDir,fileName);
part.write(file.getAbsolutePath());

files.add(file);

/* ANALYZE */

List<FileInfo> analyzedFiles=

ImageAnalyzer
.analyze(files);


/* SEND RESULT */

request.setAttribute(
"files",
analyzedFiles
);

request
.getRequestDispatcher(
"result.jsp"
)

.forward(
request,
response
);

}

}
}