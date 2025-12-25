package controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.Compras;
import modelo.CompraDetalle;
import modelo.Proveedor;
import modelo.Usuario;
import modelo.Libros;

import modeloDAO.ComprasDAO;
import modeloDAO.LibrosDAO;
import modeloDAO.CompraDetalleDAO;

@WebServlet("/ControladorCompras")
public class ControladorCompras extends HttpServlet {
    private static final long serialVersionUID = 1L;

    ComprasDAO comprasDAO = new ComprasDAO();
    CompraDetalleDAO detalleDAO = new CompraDetalleDAO();

    public ControladorCompras() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        // LISTAR COMPRAS
        if (accion == null || accion.equals("listar")) {
        	response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=compras");
            return;
        }

        // VER DETALLE DE COMPRA
        if ("detalle".equals(accion)) {
            int idCompra = Integer.parseInt(request.getParameter("id"));

            response.sendRedirect(request.getContextPath()+ "/DashboardServlet?view=comprasDetalle&id=" + idCompra);
        }else if ("modalDetalle".equals(accion)) {

            int idCompra = Integer.parseInt(request.getParameter("id"));

            request.setAttribute("listaDetalle",
                detalleDAO.listarPorCompra(idCompra));

            request.getRequestDispatcher("views/comprasDetalle.jsp")
                   .forward(request, response);
        }else if ("eliminar".equals(accion)) {

            int idCompra = Integer.parseInt(request.getParameter("id"));
            // borrar detalles
            detalleDAO.eliminarPorCompra(idCompra);
            // borrar compra
            comprasDAO.eliminar(idCompra);
            // volver a la lista
            response.sendRedirect(
                request.getContextPath() + "/DashboardServlet?view=compras"
            );
            return;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            int idProveedor = Integer.parseInt(request.getParameter("proveedor"));
            Date fecha = Date.valueOf(request.getParameter("fecha"));

            // Crear compra
            Proveedor proveedor = new Proveedor();
            proveedor.setId(idProveedor);

            HttpSession session = request.getSession();
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

            Compras compra = new Compras();
            compra.setProveedor(proveedor);
            compra.setFecha(fecha);
            compra.setTotalCompra(BigDecimal.ZERO);
            compra.setUsuario(usuario);
            comprasDAO.add(compra);
            int idCompra = comprasDAO.obtenerUltimoId();

            // PROCESAR LOS DETALLES
            String[] libros = request.getParameterValues("libros[]");
            String[] cantidades = request.getParameterValues("cantidad[]");
            String[] preciosCompra = request.getParameterValues("precioCompra[]");

            if (libros != null) {
                for (int i = 0; i < libros.length; i++) {
                    CompraDetalle detalle = new CompraDetalle();
                    
                    Compras c = new Compras();
                    c.setIdCompra(idCompra);
                    detalle.setCompras(c);

                    Libros libro = new Libros();
                    libro.setIdLibro(Integer.parseInt(libros[i]));
                    detalle.setLibro(libro);

                    detalle.setCantidad(Integer.parseInt(cantidades[i]));
                    detalle.setPrecioUniCompra(new BigDecimal(preciosCompra[i]));

                    detalleDAO.add(detalle);
                    //PARTE PARA ACTUALIZAR STOCK
                    detalleDAO.add(detalle);

                    LibrosDAO librosDAO = new LibrosDAO();
                    int idLibro = detalle.getLibro().getIdLibro();
                    int cantidad = detalle.getCantidad();

                    int stockActual = librosDAO.obtenerStock(idLibro);
                    librosDAO.actualizarStock(idLibro, stockActual + cantidad);
                }
            }

            // Actualizar total
            comprasDAO.actualizarTotal(idCompra);

            response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=compras&id=" + idCompra);
            return;
        } else if ("agregarDetalle".equals(accion)) {

            int idCompra = Integer.parseInt(request.getParameter("idCompra"));
            int idLibro = Integer.parseInt(request.getParameter("libro"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            BigDecimal precio = new BigDecimal(request.getParameter("precio"));

            Compras compra = new Compras();
            compra.setIdCompra(idCompra);

            Libros libro = new Libros();
            libro.setIdLibro(idLibro);

            CompraDetalle detalle = new CompraDetalle();
            detalle.setCompras(compra);
            detalle.setLibro(libro);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUniCompra(precio);

            detalleDAO.add(detalle);

            comprasDAO.actualizarTotal(idCompra);

            response.sendRedirect(
                request.getContextPath()
                + "/DashboardServlet?view=comprasDetalle&id=" + idCompra
            );
            return;
        }
    }
}
