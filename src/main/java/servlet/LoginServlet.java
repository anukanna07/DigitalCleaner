package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Always create/get session
        HttpSession session = request.getSession();

        // For now simple check (since no database)
        if(username != null && password != null) {

        	session.setAttribute("username", username);
        	session.setAttribute("loggedIn", true);   // ✅ ADD THIS
            response.sendRedirect("userDashboard.jsp");

        } else {
            response.sendRedirect("login.html?error=1");
        }
    }
}