package login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String u = request.getParameter("u");
        String p = request.getParameter("p");

        DBOper d = new DBOper();
        int value = d.login(u, p);

        if(value!=-1) { // user logat
            HttpSession session = request.getSession();
            session.setAttribute("userid", value);
            System.out.println("LoginServlet: bravoooo");
//            String name = request.getParameter( "u" );
//            session.setAttribute( "user", name );
//            String username= (String)session.getAttribute("user");
//            response.setContentType("text/html");
//            PrintWriter writer = response.getWriter();

            Cookie loginCookie = new Cookie("u", u);
            //setting cookie to expiry in 30 mins
            loginCookie.setMaxAge(60);
            response.addCookie(loginCookie);
            response.sendRedirect("fileupload.jsp");


            String display = d.displayUser(value);
            System.out.println(display);
            //response.sendRedirect("fileupload.jsp");

        }
        else
        {

            System.out.println("LoginServlet: user/pwd NOT FOUND in db, retry a new one on the UI ");
            String back = "/login.html";
            HttpSession session = request.getSession();
            session.removeAttribute("userid");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(back);
            dispatcher.forward(request, response);
        }

    }

}
