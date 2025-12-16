<%@page import="modelo.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>

<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

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

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=inicio"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "inicio".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-gauge-high w-4 text-center"></i>
            <span>Dashboard</span>
        </a>

        <p class="text-slate-500 uppercase text-xs tracking-wider mt-5 mb-2">Gestión</p>

        <% if ("admin".equalsIgnoreCase(rol)) { %>
        <a href="<%= request.getContextPath() %>/DashboardServlet?view=usuarios"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "usuarios".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-users w-4 text-center"></i>
            <span>Usuarios</span>
        </a>
        <% } %>

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=categorias"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "categorias".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-tags w-4 text-center"></i>
            <span>Categorías</span>
        </a>

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=productos"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "productos".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-box w-4 text-center"></i>
            <span>Productos</span>
        </a>

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=clientes"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "clientes".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-user-group w-4 text-center"></i>
            <span>Clientes</span>
        </a>

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=proveedores"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "proveedores".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-truck w-4 text-center"></i>
            <span>Proveedores</span>
        </a>

        <!-- OPERACIONES -->
        <p class="text-slate-500 uppercase text-xs tracking-wider mt-5 mb-2">Operaciones</p>

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=compra"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "compra".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-cart-shopping w-4 text-center"></i>
            <span>Compra</span>
        </a>

        <a href="<%= request.getContextPath() %>/DashboardServlet?view=venta"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
           <%= "venta".equals(view)
                ? "active bg-sky-600 text-white font-medium"
                : "text-slate-300 hover:bg-slate-700 hover:text-white" %>">
            <i class="fa-solid fa-cash-register w-4 text-center"></i>
            <span>Venta</span>
        </a>

        <hr class="border-slate-700 mt-6 mb-3">

        <!-- LOGOUT -->
        <a href="<%= request.getContextPath() + "/logout.jsp" %>"
           class="nav-link flex items-center gap-3 px-3 py-2 rounded-lg
                  text-slate-300 hover:bg-red-600 hover:text-white">
            <i class="fa-solid fa-right-from-bracket w-4 text-center"></i>
            <span>Cerrar sesión</span>
        </a>

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
	</body>
</html>