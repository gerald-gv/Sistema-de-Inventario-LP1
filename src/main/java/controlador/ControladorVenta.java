package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Cliente;
import modelo.DetalleVenta;
import modelo.Factura;
import modelo.Usuario;
import modeloDAO.ClienteDAO;
import modeloDAO.FacturaDAO;
import modeloDAO.LibrosDAO;

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
            boolean ok = dao.eliminar(id); // elimina una sola vez
            System.out.println("Factura eliminada: " + ok);
            response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=venta");
            return;
        }
        if ("generarPDF".equals(accion)) {
            int idFactura = Integer.parseInt(request.getParameter("id"));

            FacturaDAO dao = new FacturaDAO();
            Factura factura = dao.obtenerInfoFactura(idFactura);
            
            // ASIGNAMOS LOS DETALLES
            List<DetalleVenta> detalles = dao.obtenerDetallesFactura(idFactura);
            factura.setDetalles(detalles);
            
            // CALCULAMOS TOTAL
            double total = detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
            factura.setTotal(total);

            FacturaPDF.generar(factura, response);
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

            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            int idUsuario = usuario.getId();

            String[] idLibros = request.getParameterValues("idLibro[]");
            String[] cantidades = request.getParameterValues("cantidad[]");
            String[] precios = request.getParameterValues("precio[]");

            if (idLibros == null || idLibros.length == 0) {
                response.sendRedirect(
                    request.getContextPath() + "/DashboardServlet?view=ventaAdd&error=sinProductos"
                );
                return;
            }

         // VALIDAR STOCK ANTES DE GUARDAR
            LibrosDAO librosDAO = new LibrosDAO();

            for (int i = 0; i < idLibros.length; i++) {
                int idLibro = Integer.parseInt(idLibros[i]);
                int cantidad = Integer.parseInt(cantidades[i]);

                int stockActual = librosDAO.obtenerStock(idLibro);

                if (stockActual < cantidad) {
                    response.sendRedirect(
                        request.getContextPath() + "/DashboardServlet?view=ventaAdd&error=stockInsuficiente"
                    );
                    return;
                }
            }

            // SI LLEGÓ AQUÍ → TODO OK → CREAR FACTURA
            FacturaDAO facturaDAO = new FacturaDAO();
            int facturaId = facturaDAO.crearFactura(
                idCliente, idUsuario, fecha, idLibros, cantidades, precios
            );

            // AHORA SÍ DESCONTAR STOCK
            for (int i = 0; i < idLibros.length; i++) {
                int idLibro = Integer.parseInt(idLibros[i]);
                int cantidad = Integer.parseInt(cantidades[i]);

                int stockActual = librosDAO.obtenerStock(idLibro);
                int nuevoStock = stockActual - cantidad;
                librosDAO.actualizarStock(idLibro, nuevoStock);
            }
            //FINAL DE ACTUALIZAR STOCK
            Factura factura = facturaDAO.obtenerInfoFactura(facturaId); // ahora devuelve Factura completa

            response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=venta");
            return;
        }
    }
}
