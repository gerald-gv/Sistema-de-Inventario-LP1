package controlador;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import modelo.Usuario;
import modelo.Rol;
import modeloDAO.UsuarioDAO;

@WebServlet(name = "ControladorUsuario", urlPatterns = {"/ControladorUsuario"})
public class ControladorUsuario extends HttpServlet {

    private static final long serialVersionUID = 1L;

    UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
        }

        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=usuarios");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {

            Usuario u = new Usuario();
            u.setUsername(request.getParameter("username"));
            u.setUserEmail(request.getParameter("email"));
            u.setPassword(request.getParameter("password"));

            Rol rol = new Rol();
            rol.setIdRol(Integer.parseInt(request.getParameter("rol")));
            u.setRol(rol);

            dao.add(u);
        }

        if ("actualizar".equals(accion)) {
            Usuario u = new Usuario();
            u.setId(Integer.parseInt(request.getParameter("id")));
            u.setUsername(request.getParameter("username"));
            u.setUserEmail(request.getParameter("email"));
            u.setPassword(request.getParameter("password"));

            Rol rol = new Rol();
            rol.setIdRol(Integer.parseInt(request.getParameter("rol")));
            u.setRol(rol);

            dao.edit(u);
        }

        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=usuarios");
    }
}