package controlador;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import modelo.Proveedor;
import modeloDAO.ProveedorDAO;

@WebServlet(name = "ControladorProveedor", urlPatterns = {"/ControladorProveedor"})
public class ControladorProveedor extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ProveedorDAO dao = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
        }

        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=proveedores");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {

            Proveedor p = new Proveedor();
            p.setNombre(request.getParameter("nombre"));
            p.setRuc(request.getParameter("ruc"));
            p.setTelefono(request.getParameter("telefono"));
            p.setDireccion(request.getParameter("direccion"));
            p.setEmail(request.getParameter("email"));

            dao.add(p);
        }

        if ("actualizar".equals(accion)) {
            Proveedor p = new Proveedor();
            p.setId(Integer.parseInt(request.getParameter("id")));
            p.setNombre(request.getParameter("nombre"));
            p.setRuc(request.getParameter("ruc"));
            p.setTelefono(request.getParameter("telefono"));
            p.setDireccion(request.getParameter("direccion"));
            p.setEmail(request.getParameter("email"));


            dao.edit(p);
        }

        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=proveedores");

    }
}