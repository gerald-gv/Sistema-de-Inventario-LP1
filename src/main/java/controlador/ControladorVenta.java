package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modeloDAO.ClienteDAO;
import modeloDAO.FacturaDAO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/ControladorVenta")
public class ControladorVenta extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ControladorVenta() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String accion = request.getParameter("accion");

	    if ("eliminar".equals(accion)) {
	        int id = Integer.parseInt(request.getParameter("id"));

	        FacturaDAO dao = new FacturaDAO();
	        dao.eliminar(id);
	        boolean ok = dao.eliminar(id);
	        System.out.println("Factura eliminada: " + ok);
	        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=venta");
	        return;
	    }

	    // CREAMOS UNA NUEVA VENTA
	    ClienteDAO dao = new ClienteDAO();
	    List<Cliente> clientes = dao.listar();

	    request.setAttribute("clientes", clientes);
	    request.setAttribute("view", "ventaAdd");
	    request.getRequestDispatcher("/layout/layout.jsp")
	           .forward(request, response);
	}

	  protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

		    String accion = request.getParameter("accion");

		    if ("guardar".equals(accion)) {
		        int idCliente = Integer.parseInt(request.getParameter("cliente"));
		        LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));

		        String[] idLibros = request.getParameterValues("idLibro[]");
		        String[] cantidades = request.getParameterValues("cantidad[]");
		        String[] precios = request.getParameterValues("precio[]");

		        if (idLibros == null || idLibros.length == 0) {
		            response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=ventaAdd&error=sinProductos");
		            return;
		        }

		        // GUARDAMOS EN LA BD
		        FacturaDAO facturaDAO = new FacturaDAO();
		        int facturaId = facturaDAO.crearFactura(idCliente, fecha, idLibros, cantidades, precios); //LO USAREMOS EN EL FUTURO PARA EL PDF

		        // REDIRIGIMOS A LA VENTANA DE VENTAS
		        response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=venta");
		    }
	    }
	}
