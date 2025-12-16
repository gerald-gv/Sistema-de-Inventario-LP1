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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
	{
		
        String email = request.getParameter("email");
        String password = request.getParameter("contrasena");
        
        if (email == null) email = "";
        if (password == null) password = "";
        
        email = email.trim(); 
        password = password.trim(); 
        
        Usuario usuarioValidado = dao.validar(email, password);
        
        if (usuarioValidado.getId() != 0) {

            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioValidado);

            // Toast de bienvenida
            session.setAttribute("toastLogin", "Bienvenido " + usuarioValidado.getUsername());

            response.sendRedirect(request.getContextPath() + "/DashboardServlet?view=inicio");

        } else {
            request.setAttribute("errorLogin", "Email o contraseña incorrectos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException 
    {
        response.sendRedirect("login.jsp");
    }
}