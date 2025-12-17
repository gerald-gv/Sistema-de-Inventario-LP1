<%@page import="modelo.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<link rel="stylesheet" href="https://unpkg.com/driver.js@0.9.8/dist/driver.min.css">
<script src="https://unpkg.com/driver.js@0.9.8/dist/driver.min.js"></script>



<style>
    .sidebar.animate {
        animation: slideIn 0.5s ease-out;
    }

    @keyframes slideIn {
        from {
            opacity: 0;
            transform: translateX(-20px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }

    .nav-link {
        transition: background-color 0.2s ease,
                    color 0.2s ease,
                    transform 0.15s ease;
    }

    .nav-link:hover {
        transform: translateX(4px);
    }

    .nav-link.active {
        box-shadow: inset 3px 0 0 #38bdf8;
    }
</style>
</head>

<body class="flex bg-slate-100 min-h-screen">

<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : "";
    String view = (String) request.getAttribute("view");
%>

	<!-- SIDEBAR -->
	<aside class="sidebar w-80 min-h-screen bg-gradient-to-b from-slate-900 to-slate-800 text-white p-6 shadow-2xl">
	
	    <!-- USUARIO -->
	    <div class="mb-6">
	        <p class="text-slate-400 text-sm">Bienvenido,</p>
	        <h2 class="text-xl font-semibold text-sky-400 flex items-center gap-2">
	            <i class="fa-solid fa-user"></i>
	            <%= usuario.getUsername() %>
	        </h2>
	    </div>
	
	    <hr class="border-slate-700 mb-6">
	
	    <nav class="flex flex-col text-sm space-y-1">
	
	        <p class="text-slate-500 uppercase text-xs tracking-wider mb-2">Principal</p>
	
	        <a id="linkInicio" href="<%= request.getContextPath() %>/DashboardServlet?view=inicio"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "inicio".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-gauge-high w-4 text-center"></i>
	            <span>Dashboard</span>
	        </a>
	
	        <p class="text-slate-500 uppercase text-xs tracking-wider mt-5 mb-2">Gestión</p>
	
	        <% if ("admin".equalsIgnoreCase(rol)) { %>
	        <a id="linkUsuarios" href="<%= request.getContextPath() %>/DashboardServlet?view=usuarios"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "usuarios".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-users w-4 text-center"></i>
	            <span>Usuarios</span>
	        </a>
	        <% } %>
	
	        <a id="linkCategorias" href="<%= request.getContextPath() %>/DashboardServlet?view=categorias"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "categorias".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-tags w-4 text-center"></i>
	            <span>Categorías</span>
	        </a>
	
	        <a id="linkProductos" href="<%= request.getContextPath() %>/DashboardServlet?view=productos"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "productos".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-box w-4 text-center"></i>
	            <span>Productos</span>
	        </a>
	
	        <a id="linkClientes" href="<%= request.getContextPath() %>/DashboardServlet?view=clientes"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "clientes".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-user-group w-4 text-center"></i>
	            <span>Clientes</span>
	        </a>
	
	        <a id="linkProveedores" href="<%= request.getContextPath() %>/DashboardServlet?view=proveedores"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "proveedores".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-truck w-4 text-center"></i>
	            <span>Proveedores</span>
	        </a>
	
	        <!-- OPERACIONES -->
	        <p class="text-slate-500 uppercase text-xs tracking-wider mt-5 mb-2">Operaciones</p>
	
	        <a id="linkCompras" href="<%= request.getContextPath() %>/DashboardServlet?view=compras"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "compra".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-cart-shopping w-4 text-center"></i>
	            <span>Compra</span>
	        </a>
	
	        <a id="linkVentas" href="<%= request.getContextPath() %>/DashboardServlet?view=venta"
	           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
	           <%= "venta".equals(view)
	                ? "active bg-sky-600 text-white font-medium"
	                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
	            <i class="fa-solid fa-cash-register w-4 text-center"></i>
	            <span>Venta</span>
	        </a>
	
	        <hr class="border-slate-700 mt-6 mb-3">
	        
	        <div class="space-y-2 pt-2 ">
		        
		        <!-- TUTORIAL -->
		        <button id="btnTutorial" onclick="iniciarTutorialAside()"
		        	class="flex items-center gap-3 p-3 rounded-lg bg-sky-600 text-white font-medium hover:bg-sky-700 hover:-translate-y-1 transition-all duration-300 w-full text-left">
				   	<i class="fa-solid fa-circle-question w-4 text-center"></i>
				    <span>Ver tutorial</span>
				</button>
		
		
		        <!-- LOGOUT -->
		        <a href="<%= request.getContextPath() + "/logout.jsp" %>"
		           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
		                  text-slate-300 hover:bg-red-600 hover:text-white">
		            <i class="fa-solid fa-right-from-bracket w-4 text-center"></i>
		            <span>Cerrar sesión</span>
		        </a>
	        </div>
	        
	
	    </nav>
	</aside>

		<!-- CONTENIDO -->
		<main class="flex-1 p-8">
    		<%
        		String viewParam = (String) request.getAttribute("view");
        		String includePath = "/views/" + viewParam + ".jsp";
    		%>
    		<jsp:include page="<%= includePath %>" />
		</main>
		
		
	<script>
		function iniciarTutorialAside() {
		    const rol = "<%= rol %>";
		
		    const driver = new Driver({
		        opacity: 0.765,                 
		        padding: 8,
		        animate: true,
		        overlayClickNext: false,
		        allowClose: false,
		        stageBackground: '#0f172a',   
		        doneBtnText: 'Finalizar',
		        closeBtnText: 'Cerrar',
		        nextBtnText: 'Siguiente',
		        prevBtnText: 'Anterior',
		    });
		
		    const steps = [
		        {
		            element: '#linkInicio',
		            popover: {
		                title: 'Dashboard',
		                description: 'Resumen general del sistema. Podras visualizar todos los registros de cada entidad',
		                position: 'right'
		            }
		        }
		    ];
		
		    if (rol === "admin") {
		        steps.push({
		            element: '#linkUsuarios',
		            popover: {
		                title: 'Usuarios',
		                description: 'Gestiona los usuarios del sistema. Podras Agregar, Modificar y Eliminar los Usuarios del sistema',
		                position: 'right'
		            }
		        });
		    }
		
		    steps.push(
		        { element: '#linkCategorias', popover: { title: 'Categorías', description: 'Gestiona categorias. Podras agregar nuevas categorias para los productos.', position: 'right' }},
		        { element: '#linkProductos', popover: { title: 'Productos', description: 'Gestiona productos. Podras agregar nuevos productos y asignarles sus cualidades', position: 'right' }},
		        { element: '#linkClientes', popover: { title: 'Clientes', description: 'Gestiona clientes. Podras Agregar, Modificar y Eliminar los clientes del sistema', position: 'right' }},
		        { element: '#linkProveedores', popover: { title: 'Proveedores', description: 'Administra proveedores. Podras Agregar, Modificar y Eliminar los proveedores del sistema', position: 'right' }},
		        { element: '#linkCompras', popover: { title: 'Compras', description: 'Registro de compras. Podras registrar nuevas solicitudes de compra en el sistema', position: 'right' }},
		        { element: '#linkVentas', popover: { title: 'Ventas', description: 'Registro de ventas. Podras registrar nuevas solicitudes de venta en el sistema ', position: 'right' }},
		        { element: '#btnTutorial', popover: { title: '¡Listo!', description: 'Ya conoces el panel principal. Puedes volver a ver este tutorial cuando quieras.', position: 'right' }}
		    );
		
		    driver.defineSteps(steps);
		    driver.start();
		}
	</script>
	</body>
</html>