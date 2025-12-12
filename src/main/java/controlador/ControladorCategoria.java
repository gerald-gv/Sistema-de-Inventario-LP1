package controlador;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import modelo.Categoria;
import modeloDAO.CategoriaDAO;

@WebServlet(name = "ControladorCategoria", urlPatterns = {"/ControladorCategoria"})
public class ControladorCategoria extends HttpServlet {

    private static final long serialVersionUID = 1L;

    CategoriaDAO dao = new CategoriaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.eliminar(id);
        }

        response.sendRedirect(request.getContextPath() + "/layout/dashboard.jsp?view=categorias");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("guardar".equals(accion)) {
            Categoria c = new Categoria();
            c.setNombre(request.getParameter("nombre"));
            c.setDescripcion(request.getParameter("descripcion"));
            dao.add(c);
        }

        if ("actualizar".equals(accion)) {
            Categoria c = new Categoria();
            c.setIdCategoria(Integer.parseInt(request.getParameter("id")));
            c.setNombre(request.getParameter("nombre"));
            c.setDescripcion(request.getParameter("descripcion"));
            dao.edit(c);
        }

        response.sendRedirect(request.getContextPath() + "/layout/dashboard.jsp?view=categorias");
    }
}