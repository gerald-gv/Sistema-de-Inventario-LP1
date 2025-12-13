package controlador;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Categoria;
import modelo.Libros;
import modeloDAO.LibrosDAO;

@WebServlet("/ControladorLibro")
public class ControladorLibro extends HttpServlet {
    private static final long serialVersionUID = 1L;

    LibrosDAO dao = new LibrosDAO();

    public ControladorLibro() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null || accion.equals("listar")) {
            request.setAttribute("listaLibros", dao.listar());
            request.getRequestDispatcher("views/productos.jsp").forward(request, response);
            return;
        }

        if (accion.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
            response.sendRedirect(request.getContextPath() + "/layout/dashboard.jsp?view=productos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String descripcion = request.getParameter("descripcion");
        BigDecimal precioCompra = new BigDecimal(request.getParameter("precioCompra"));
        BigDecimal precioVenta = new BigDecimal(request.getParameter("precioVenta"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int idCategoria = Integer.parseInt(request.getParameter("categoria"));

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(idCategoria);

        Libros libro = new Libros();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setDescripcion(descripcion);
        libro.setPrecioCompra(precioCompra);
        libro.setPrecioVenta(precioVenta);
        libro.setStock(stock);
        libro.setIdCat(categoria);

        if ("guardar".equals(accion)) {
            dao.add(libro);
        }

        if ("actualizar".equals(accion)) {
            int idLibro = Integer.parseInt(request.getParameter("id"));
            libro.setIdLibro(idLibro);
            dao.edit(libro);
        }

        response.sendRedirect(request.getContextPath() + "/layout/dashboard.jsp?view=productos");
    }
}