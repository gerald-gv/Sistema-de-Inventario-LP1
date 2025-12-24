package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cliente;
import modelo.Factura;
import modeloDAO.CategoriaDAO;
import modeloDAO.ClienteDAO;
import modeloDAO.ComprasDAO;
import modeloDAO.FacturaDAO;
import modeloDAO.LibrosDAO;
import modeloDAO.ProveedorDAO;
import modeloDAO.UsuarioDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			ClienteDAO clientesDAO = new ClienteDAO();
			CategoriaDAO categoriaGAO = new CategoriaDAO();
			LibrosDAO librosDAO = new LibrosDAO();
			ProveedorDAO proveedoresDAO = new ProveedorDAO();
			ComprasDAO comprasDAO = new ComprasDAO();
			FacturaDAO ventasDAO = new FacturaDAO();
			
			int totalUsuarios = usuarioDAO.count();
			int totalClientes = clientesDAO.count();
			int totalCategorias = categoriaGAO.count();
			int totalLibros = librosDAO.count();
			int stockTotal = librosDAO.totalStock();
			int totalProveedores = proveedoresDAO.count();
			int totalComprasRegis = comprasDAO.count();
			int totalVentasRegis = ventasDAO.count();
			BigDecimal totalImporteCompra = comprasDAO.sumaTotalCompras();
			BigDecimal totalImporteVenta = ventasDAO.sumaTotalVentas();
			
			request.setAttribute("totalUsuarios", totalUsuarios);
			request.setAttribute("totalClientes", totalClientes);
			request.setAttribute("totalCategorias", totalCategorias);
			request.setAttribute("totalLibros", totalLibros);
			request.setAttribute("stockTotal", stockTotal);
			request.setAttribute("totalProveedores", totalProveedores);
			request.setAttribute("comprasRegistradas", totalComprasRegis);
			request.setAttribute("ventasRegistradas", totalVentasRegis);
			request.setAttribute("importeTotalCompras", totalImporteCompra);
			request.setAttribute("importeTotalVentas", totalImporteVenta);
			
		}else if ("ventaAdd".equalsIgnoreCase(view)) {

	        ClienteDAO clienteDAO = new ClienteDAO();
	        List<Cliente> clientes = clienteDAO.listar();
	        request.setAttribute("clientes", clientes);

	    } else if ("venta".equalsIgnoreCase(view)) {

	        FacturaDAO facturaDAO = new FacturaDAO();
	        List<Factura> ventas = facturaDAO.listar();
	        request.setAttribute("ventas", ventas);
	    }

	    request.setAttribute("view", view);
	    request.getRequestDispatcher("/layout/dashboard.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
