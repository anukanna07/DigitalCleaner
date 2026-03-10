package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DeletePermanentServlet")
public class DeletePermanentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("fileName");

        String vaultPath = getServletContext().getRealPath("/RecoverVault");

        File file = new File(vaultPath, fileName);

        if(file.exists()){
            file.delete();
        }

        response.sendRedirect("RecoverVault.jsp");
    }
}