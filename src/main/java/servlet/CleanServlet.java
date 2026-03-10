package servlet;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CleanServlet")
public class CleanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("loggedIn") == null){
            response.sendRedirect("login.html");
            return;
        }

        String[] selectedFiles = request.getParameterValues("selectedFiles");

        int deletedCount = 0;
        long totalSavedBytes = 0;

        // ===== Recover Vault Folder =====
        String vaultPath = getServletContext().getRealPath("/RecoverVault");
        File vaultFolder = new File(vaultPath);

        if(!vaultFolder.exists()){
            vaultFolder.mkdir();
        }

        if(selectedFiles != null){
            for(String path : selectedFiles){

                File file = new File(path);

                if(file.exists()){

                    totalSavedBytes += file.length();

                    File destination = new File(vaultFolder, file.getName());

                    try{
                        Files.move(file.toPath(),
                                   destination.toPath(),
                                   StandardCopyOption.REPLACE_EXISTING);

                        deletedCount++;

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        long totalSavedKB = totalSavedBytes / 1024;

        // Send to cleanResult.jsp
        request.setAttribute("deletedCount", deletedCount);
        request.setAttribute("spaceSaved", totalSavedKB);

        // ===== UPDATE SESSION DASHBOARD STATS =====
        Integer sessionDeleted = (Integer) session.getAttribute("totalDeleted");
        Long sessionSaved = (Long) session.getAttribute("totalSaved");

        if(sessionDeleted == null) sessionDeleted = 0;
        if(sessionSaved == null) sessionSaved = 0L;

        sessionDeleted += deletedCount;
        sessionSaved += totalSavedKB;

        session.setAttribute("totalDeleted", sessionDeleted);
        session.setAttribute("totalSaved", sessionSaved);

        request.getRequestDispatcher("cleanResult.jsp")
               .forward(request, response);
    }
}