<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario user = (Usuario) session.getAttribute("usuarioLogueado");
    if (user == null || !user.getRol().getNombre().equalsIgnoreCase("admin")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard Administrador</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-green-50 p-8">
    <div class="max-w-4xl mx-auto bg-white p-10 rounded-xl shadow-2xl">
        <h1 class="text-4xl font-bold text-green-700 mb-4">Bienvenido, <%= user.getUsername() %></h1>
        <p class="text-xl text-gray-600 mb-6">Usted ha iniciado sesión como <span class="font-bold text-green-500"><%= user.getRol().getNombre() %></span>.</p>
        
        <div class="bg-green-100 p-6 rounded-lg border-l-4 border-green-500">
            <h2 class="text-2xl font-semibold text-green-800 mb-3">Funcionalidades de Administrador:</h2>
            <ul class="list-disc list-inside space-y-1 text-gray-700">
                <li>Gestión de Usuarios y Roles (Creación de nuevos roles, etc.)</li>
                <li>Reportes Financieros (Ventas, Compras)</li>
                <li>Gestión de Inventario Completa</li>
            </ul>
        </div>
        
        <div class="mt-8">
            <a href="logout.jsp" class="inline-block bg-red-500 text-white font-bold py-2 px-4 rounded hover:bg-red-600 transition duration-300">Cerrar Sesión</a>
        </div>
    </div>
</body>
</html>