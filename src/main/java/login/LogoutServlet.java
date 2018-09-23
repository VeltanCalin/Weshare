package login;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        //Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
               // if (cookie.getName().equals("u"))
                   // loginCookie = cookie;
                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");

//
//
            response.addCookie(cookie);

            }
        String back = "login.html";
        response.sendRedirect(back);
        }


//        HttpSession session = request.getSession();
//        session.removeAttribute("userid");

//        if (loginCookie != null) {
//            loginCookie.setMaxAge(0);
//            loginCookie.setValue(null);
//            loginCookie.setPath("/");
//
//
//            response.addCookie(loginCookie);
//        }


        }
}




