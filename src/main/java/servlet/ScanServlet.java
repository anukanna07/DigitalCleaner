package servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import javax.servlet.http.Part;

@WebServlet("/ScanServlet")
@MultipartConfig
public class ScanServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Session check
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("loggedIn") == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Get uploaded files
        Collection<Part> files = request.getParts();

        int count = 0;

        for(Part file : files){

            if(file.getSubmittedFileName()!=null &&
               file.getSubmittedFileName().length()>0){

                count++;
            }
        }

        request.setAttribute("totalScreenshots", count);

        request.getRequestDispatcher("result.jsp")
               .forward(request,response);
    }
}