<%@page import="modelo.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="flex bg-gray-100 min-h-screen">

	<%
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
		
		//Validacion de existencia de usuario
		if(usuario == null){
			response.sendRedirect("login.jsp");
			return;
		}
		
		String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : " " ;
		
	%>
	
	
	
<body class="flex bg-gray-100 min-h-screen">

	<!-- SIDEBAR -->
	<aside class="w-80 h-screen bg-gradient-to-b from-slate-800 to-slate-900 text-white p-6 shadow-xl">
	
	    <!-- HEADER DEL USUARIO -->
	    <h2 class="text-xl font-semibold mb-1">Bienvenido,</h2>
	    <p class="text-sky-300 text-lg font-medium mb-4"><%= usuario.getUsername() %></p>
	
	    <hr class="border-blue-700 mb-6 ">
	
	    <nav class="flex flex-col space-y-2 text-sm">
	
	        <!-- SECCIÓN: PRINCIPAL -->
	        <p class="text-slate-400 uppercase text-xs tracking-wider mb-1">Principal</p>
	
	        <a href="dashboard.jsp?view=inicio"
	           class="p-2 rounded-lg hover:bg-slate-700 transition">Dashboard</a>
	
	        <!-- SECCIÓN: GESTIÓN -->
	        <p class="text-slate-400 uppercase text-xs tracking-wider mt-4 mb-1">Gestión</p>
	
	        <% if ("admin".equalsIgnoreCase(rol)) { %>
	        <a href="dashboard.jsp?view=usuarios" class=" p-2 text-sky-300 font-semibold rounded-lg hover:bg-sky-700 hover:text-sky-200  transition">
	            Usuarios
	        </a>
	        <% } %>
	
	        <a href="dashboard.jsp?view=categorias" class="font-medium p-2 rounded-lg hover:bg-slate-700 transition">Categorias</a>
	
	        <a href="dashboard.jsp?view=productos" class="font-medium p-2 rounded-lg hover:bg-slate-700 transition">Productos</a>
	
	        <a href="dashboard.jsp?view=clientes" class=" font-medium p-2 rounded-lg hover:bg-slate-700 transition">Clientes</a>
	
	        <a href="dashboard.jsp?view=proveedores" class="font-medium p-2 rounded-lg hover:bg-slate-700 transition">Proveedores</a>
	
	        <!-- SECCIÓN: OPERACIONES -->
	        <p class="text-slate-400 uppercase text-xs tracking-wider mt-4 mb-1">Operaciones</p>
	
	        <a href="dashboard.jsp?view=compra" class="font-medium p-2 rounded-lg hover:bg-slate-700 transition">Compra</a>
	
	        <a href="dashboard.jsp?view=venta" class="font-medium p-2 rounded-lg hover:bg-slate-700 transition">Venta</a>
	
	        <!-- SECCIÓN FINAL -->
	        <hr class="border-blue-700 mt-6 mb-3">
	
	        <a href="<%= request.getContextPath() + "/logout.jsp" %>" class="p-2 rounded-lg hover:bg-red-600 hover:text-white transition-colors duration-300 text-slate-300">
	            Cerrar sesión
	        </a>
	
	    </nav>
	</aside>


    <!-- CONTENIDO DINAMICO -->
    <main class="flex-1 p-8">
        <%
            
            String viewParam = request.getParameter("view");
        
            if (viewParam == null || viewParam.trim().isEmpty()) {
                viewParam = "inicio";
            }
            
            String includePath = "/views/" + viewParam + ".jsp";
        %>

        <jsp:include page="<%= includePath %>" />
    </main>
	
</body>
</html>