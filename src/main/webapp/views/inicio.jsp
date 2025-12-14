<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="modelo.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

%>


<div class="mb-12">

    <h1 class="text-2xl font-bold text-slate-800">Dashboard</h1>
    <p class="text-gray-600">Bienvenido, <%= usuario.getUsername() %> — Rol: <%= usuario.getRol().getNombre() %></p>

</div>

<section class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">

    <!-- Clientes -->
    <div class="bg-white border-l-4 border-sky-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
        <div class="flex items-center justify-between">
            <div>
                <p class="text-sm text-slate-500 uppercase">Clientes</p>
                <h2 class="text-3xl font-bold text-slate-800">${totalClientes}</h2>
            </div>

            <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-sky-100 text-sky-600 text-3xl">
                👤
            </div>
        </div>
    </div>

    <!-- Categorías -->
    <div class="bg-white border-l-4 border-indigo-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
        <div class="flex items-center justify-between">
            <div>
                <p class="text-sm text-slate-500 uppercase">Categorías</p>
                <h2 class="text-3xl font-bold text-slate-800">8</h2>
            </div>

            <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-indigo-100 text-indigo-600 text-3xl">
                🗂️
            </div>
        </div>
    </div>

    <!-- Productos -->
    <div class="bg-white border-l-4 border-emerald-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
        <div class="flex items-center justify-between">
            <div>
                <p class="text-sm text-slate-500 uppercase">Productos</p>
                <h2 class="text-3xl font-bold text-slate-800">${totalLibros}</h2>
            </div>

            <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-emerald-100 text-emerald-600 text-3xl">
                📦
            </div>
        </div>
    </div>

    <!-- Stock -->
    <div class="bg-white border-l-4 border-amber-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
        <div class="flex items-center justify-between">
            <div>
                <p class="text-sm text-slate-500 uppercase">Stock total</p>
                <h2 class="text-3xl font-bold text-slate-800">${stockTotal}</h2>
            </div>

            <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-amber-100 text-amber-600 text-3xl">
                📊
            </div>
        </div>
    </div>
    
    <!-- Proveedores -->
    <div class="bg-white border-l-4 border-purple-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
    	<div class="flex items-center justify-between">
        	<div>
            	<p class="text-sm text-slate-500 uppercase">Proveedores</p>
            	<h2 class="text-3xl font-bold text-slate-800">${totalProveedores}</h2>
        	</div>

        	<div class="w-14 h-14 flex items-center justify-center rounded-lg bg-purple-100 text-purple-600 text-3xl">
            🚚
        	</div>
    	</div>
	</div>
    
    

</section>


