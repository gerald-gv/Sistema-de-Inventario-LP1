<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="modelo.Usuario"%>
<%
Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : " ";
%>


<div class="mb-12">

	<h1 class="text-2xl font-bold text-slate-800">Dashboard</h1>
	<p class="text-gray-600"> Bienvenido, <%=usuario.getUsername()%> — Rol: <%=usuario.getRol().getNombre()%></p>

</div>

<section class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">


	<%
	if ("admin".equalsIgnoreCase(rol)) {
	%>

	<!-- Usuarios -->
	<div class="bg-white border-l-4 border-indigo-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
		<div class="flex items-center justify-between">
			<div>
				<p class="text-sm text-slate-500 uppercase">Usuarios</p>
				<h2 class="text-3xl font-bold text-slate-800">${totalUsuarios}</h2>
			</div>

			<div class="w-14 h-14 flex items-center justify-center rounded-lg bg-indigo-100 text-indigo-600 text-3xl">
				<i class="fa-solid fa-users"></i>
			</div>
		</div>
	</div>
	<%
	}
	%>


	<!-- Clientes -->
	<div class="bg-white border-l-4 border-sky-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
		<div class="flex items-center justify-between">
			<div>
				<p class="text-sm text-slate-500 uppercase">Clientes</p>
				<h2 class="text-3xl font-bold text-slate-800">${totalClientes}</h2>
			</div>

			<div
				class="w-14 h-14 flex items-center justify-center rounded-lg bg-sky-100 text-sky-600 text-3xl">
				<i class="fa-solid fa-user"></i>
			</div>
		</div>
	</div>

	<!-- Categorías -->
	<div class="bg-white border-l-4 border-indigo-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
		<div class="flex items-center justify-between">
			<div>
				<p class="text-sm text-slate-500 uppercase">Categorías</p>
				<h2 class="text-3xl font-bold text-slate-800">${totalCategorias}</h2>
			</div>

			<div
				class="w-14 h-14 flex items-center justify-center rounded-lg bg-indigo-100 text-indigo-600 text-3xl">
				<i class="fa-solid fa-folder-open"></i>
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

			<div
				class="w-14 h-14 flex items-center justify-center rounded-lg bg-emerald-100 text-emerald-600 text-3xl">
				<i class="fa-solid fa-box"></i>
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

			<div
				class="w-14 h-14 flex items-center justify-center rounded-lg bg-amber-100 text-amber-600 text-3xl">
				<i class="fa-solid fa-chart-column"></i>
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

			<div
				class="w-14 h-14 flex items-center justify-center rounded-lg bg-purple-100 text-purple-600 text-3xl">
				<i class="fa-solid fa-truck"></i>
			</div>
		</div>
	</div>
	
	<!-- Total de Compras Registradas -->
	<div class="bg-white border-l-4 border-orange-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
	    <div class="flex items-center justify-between">
	        <div>
	            <p class="text-sm text-slate-500 uppercase">Total Compras</p>
	            <h2 class="text-3xl font-bold text-slate-800">${comprasRegistradas}</h2>
	        </div>
	
	        <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-orange-100 text-orange-600 text-3xl">
	            <i class="fa-solid fa-file-invoice"></i>
	        </div>
	    </div>
	</div>
	
	<!-- Total Ventas Registradas -->
	<div class="bg-white border-l-4 border-blue-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
	    <div class="flex items-center justify-between">
	        <div>
	            <p class="text-sm text-slate-500 uppercase">Total Ventas</p>
	            <h2 class="text-3xl font-bold text-slate-800">8</h2>
	        </div>
	
	        <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-blue-100 text-blue-600 text-3xl">
	            <i class="fa-solid fa-receipt"></i>
	        </div>
	    </div>
	</div>

	<!-- Importe de Compras -->
	<div class="bg-white border-l-4 border-red-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
		<div class="flex items-center justify-between">
			<div>
				<p class="text-sm text-slate-500 uppercase">Importe de Compras</p>
				<h2 class="text-3xl font-bold text-slate-800">${importeTotalCompras}</h2>
			</div>

			<div
				class="w-14 h-14 flex items-center justify-center rounded-lg bg-red-100 text-red-600 text-3xl">
				<i class="fa-solid fa-cart-shopping"></i>
			</div>
		</div>
	</div>
	
	<!-- Importe de Ventas -->
	<div class="bg-white border-l-4 border-emerald-500 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
	    <div class="flex items-center justify-between">
	        <div>
	            <p class="text-sm text-slate-500 uppercase">Importe de Ventas</p>
	            <h2 class="text-3xl font-bold text-slate-800"> 50</h2>
	        </div>
	
	        <div
	            class="w-14 h-14 flex items-center justify-center rounded-lg bg-emerald-100 text-emerald-600 text-3xl">
	            <i class="fa-solid fa-hand-holding-dollar"></i>
	        </div>
	    </div>
	</div>
	
	<!-- Ganancia -->
	<div class="bg-white border-l-4 border-emerald-600 rounded-xl shadow-sm p-5 transition-all duration-300 hover:shadow-lg hover:-translate-y-1">
	    <div class="flex items-center justify-between">
	        <div>
	            <p class="text-sm text-slate-500 uppercase">Ganancia</p>
	            <h2 class="text-3xl font-bold text-slate-800">30</h2>
	        </div>
	
	        <div class="w-14 h-14 flex items-center justify-center rounded-lg bg-emerald-100 text-emerald-700 text-3xl">
	            <i class="fa-solid fa-arrow-trend-up"></i>
	        </div>
	    </div>
	</div>
</section>