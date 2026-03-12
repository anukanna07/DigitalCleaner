package servlet;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CleanServlet")

public class CleanServlet extends HttpServlet{

protected void doPost(
HttpServletRequest request,
HttpServletResponse response)

throws ServletException, IOException{


HttpSession session =
request.getSession(false);

if(session==null ||
session.getAttribute("loggedIn")==null){

response.sendRedirect("login.html");

return;

}


String[] selectedFiles =
request.getParameterValues("selectedFiles");

int deletedCount = 0;

long totalSavedBytes = 0;


/* ===== RENDER SAFE PATHS ===== */

String uploadPath =
"/tmp/uploads";

String vaultPath =
"/tmp/RecoverVault";


File vaultFolder =
new File(vaultPath);

if(!vaultFolder.exists()){

vaultFolder.mkdirs();

}


/* ===== PROCESS FILES ===== */

/* ===== PROCESS FILES ===== */

if(selectedFiles!=null){

for(String fileName:selectedFiles){

fileName =
java.net.URLDecoder.decode(
fileName,"UTF-8");

File file =
new File(uploadPath,fileName);

System.out.println("Trying: "+file.getAbsolutePath());

if(file.exists()){

totalSavedBytes +=
file.length();

File destination =
new File(vaultFolder,fileName);

try{

Files.move(
file.toPath(),
destination.toPath(),
StandardCopyOption.REPLACE_EXISTING
);

deletedCount++;

}catch(Exception e){

e.printStackTrace();

}

}else{

System.out.println("File not found");

}

}



}


long totalSavedKB =
totalSavedBytes/1024;


/* ===== RESULT DATA ===== */

request.setAttribute(
"deletedCount",
deletedCount
);

request.setAttribute(
"spaceSaved",
totalSavedKB
);


/* ===== SESSION DASHBOARD UPDATE ===== */

Integer sessionDeleted =
(Integer)session.getAttribute(
"totalDeleted"
);

Long sessionSaved =
(Long)session.getAttribute(
"totalSaved"
);


if(sessionDeleted==null)
sessionDeleted=0;

if(sessionSaved==null)
sessionSaved=0L;


sessionDeleted +=
deletedCount;

sessionSaved +=
totalSavedKB;


session.setAttribute(
"totalDeleted",
sessionDeleted
);

session.setAttribute(
"totalSaved",
sessionSaved
);


/* ===== FORWARD RESULT ===== */

request
.getRequestDispatcher(
"cleanResult.jsp"
)

.forward(
request,
response
);

}

}