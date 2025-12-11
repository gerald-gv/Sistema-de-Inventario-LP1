<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
%>

<h1 class="text-3xl font-bold mb-4">Inicio</h1>
<p class="text-gray-600">Bienvenido, <%= usuario.getUsername() %> — Rol: <%= usuario.getRol().getNombre() %></p>
