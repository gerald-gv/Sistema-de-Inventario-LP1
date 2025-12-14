package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modeloDAO.ClienteDAO;

import java.io.IOException;

/**
 * Servlet implementation class ControladorCliente
 */
@WebServlet("/ControladorCliente")
public class ControladorCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ClienteDAO dao = new ClienteDAO();
	
    public ControladorCliente() {
        super();
       
    }
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
        }

        // Redirigir al DashboardServlet para actualizar métricas
        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=clientes");
    }

	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            Cliente c = new Cliente();
            c.setNombreNegocio(request.getParameter("nombreNegocio"));
            c.setEmail(request.getParameter("email"));
            c.setContacto(request.getParameter("contacto"));
            c.setDireccion(request.getParameter("direccion"));
            c.setTipoCliente(request.getParameter("tipoCliente"));
            dao.add(c);
        }

        if ("actualizar".equals(accion)) {
            Cliente c = new Cliente();
            c.setId(Integer.parseInt(request.getParameter("id")));
            c.setNombreNegocio(request.getParameter("nombreNegocio"));
            c.setEmail(request.getParameter("email"));
            c.setContacto(request.getParameter("contacto"));
            c.setDireccion(request.getParameter("direccion"));
            c.setTipoCliente(request.getParameter("tipoCliente"));
            dao.edit(c);
        }

        // Redirigir al DashboardServlet para actualizar métricas
        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=clientes");
    }

}
