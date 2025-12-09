package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import modelo.Usuario;
import modeloDAO.UsuarioDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UsuarioDAO dao = new UsuarioDAO(); 

    public LoginServlet() {
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
	{
		
        String email = request.getParameter("email").trim(); 
        String password = request.getParameter("contrasena").trim();
        
        Usuario usuarioValidado = dao.validar(email, password);
        
        if (usuarioValidado.getId() != 0) {
            
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioValidado);
            
            String rolNombre = usuarioValidado.getRol().getNombre();
            
            if (rolNombre.equalsIgnoreCase("Administrador")) {
                response.sendRedirect("dashboard_admin.jsp");
            } else if (rolNombre.equalsIgnoreCase("Empleado")) {
                response.sendRedirect("dashboard_empleado.jsp");
            } else {
                response.sendRedirect("login.jsp"); 
            }
            
        } else {
            request.setAttribute("errorLogin", "Email o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
    {
        response.sendRedirect("login.jsp");
    }

}