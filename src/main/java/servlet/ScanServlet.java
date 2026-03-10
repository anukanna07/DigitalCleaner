package servlet;

import java.io.IOException;
import java.io.File;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import utils.FileScanner;
import utils.ImageAnalyzer;
import utils.FileInfo;

@WebServlet("/ScanServlet")
public class ScanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // 🔒 Session Protection
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String folderPath = request.getParameter("folderPath");

        List<File> files = FileScanner.getScreenshotFiles(folderPath);
        List<FileInfo> analyzedFiles = ImageAnalyzer.analyze(files);

        request.setAttribute("files", analyzedFiles);

        request.getRequestDispatcher("result.jsp")
               .forward(request, response);
    }
}