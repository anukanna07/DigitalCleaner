package servlet;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.util.Collection;

import utils.ImageAnalyzer;
import utils.FileInfo;

@WebServlet("/ScanServlet")

@MultipartConfig(
fileSizeThreshold=1024*1024*2,
maxFileSize=1024*1024*50,
maxRequestSize=1024*1024*100
)

public class ScanServlet extends HttpServlet {

protected void doPost(
HttpServletRequest request,
HttpServletResponse response)

throws ServletException, IOException {


HttpSession session =
request.getSession(false);

if(session==null ||
session.getAttribute("loggedIn")==null){

response.sendRedirect("login.html");

return;

}

/* GET UPLOADED FILES */

Collection<Part> parts =
request.getParts();

List<File> files =
new ArrayList<>();

/* temp upload folder */

String uploadPath =
getServletContext()
.getRealPath("")
+File.separator+
"tempUploads";

File uploadDir =
new File(uploadPath);

if(!uploadDir.exists()){

uploadDir.mkdir();

}

/* save uploaded images */

for(Part part : parts){

String fileName =
part.getSubmittedFileName();

if(fileName!=null &&
fileName.endsWith(".png") ||
fileName.endsWith(".jpg") ||
fileName.endsWith(".jpeg")){

File file =
new File(uploadPath+
File.separator+
fileName);

part.write(
file.getAbsolutePath()
);

files.add(file);

}

}

/* analyze images */

List<FileInfo> analyzedFiles =
ImageAnalyzer.analyze(files);

request.setAttribute(
"files",
analyzedFiles
);

request
.getRequestDispatcher("result.jsp")
.forward(request,response);

}
}
