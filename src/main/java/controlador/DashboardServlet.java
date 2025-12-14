package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modeloDAO.ClienteDAO;
import modeloDAO.LibrosDAO;
import modeloDAO.ProveedorDAO;

import java.io.IOException;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Controlador de Vistas
		String view = request.getParameter("view");
		
		if(view == null || view.isEmpty()) {
			view = "inicio";
		}
		
		// Metricas
		
		if("inicio".equalsIgnoreCase(view)) {
			
			ClienteDAO clientesDAO = new ClienteDAO();
			LibrosDAO librosDAO = new LibrosDAO();
			ProveedorDAO proveedoresDAO = new ProveedorDAO();
			
			int totalClientes = clientesDAO.count();
			int totalLibros = librosDAO.count();
			int stockTotal = librosDAO.totalStock();
			int totalProveedores = proveedoresDAO.count();
			
			
			request.setAttribute("totalClientes", totalClientes);
			request.setAttribute("totalLibros", totalLibros);
			request.setAttribute("stockTotal", stockTotal);
			request.setAttribute("totalProveedores", totalProveedores);
			
		}
		request.setAttribute("view", view);

        request.getRequestDispatcher("/layout/dashboard.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
