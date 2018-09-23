

<%

    HttpSession s = request.getSession();
    Object o = s.getAttribute("userid");
    if(o==null)
    {
        response.sendRedirect("login.html");
    }
%>

