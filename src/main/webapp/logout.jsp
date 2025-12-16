<%
    session.invalidate();
    session = request.getSession(true);
    session.setAttribute("toastLogout", "Sesión cerrada correctamente");
    response.sendRedirect("login.jsp");
%>