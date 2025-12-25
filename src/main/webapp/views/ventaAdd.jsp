<%@page import="modelo.Libros"%>
<%@page import="modeloDAO.LibrosDAO"%>
<%@page import="modelo.Cliente"%>
<%@page import="java.util.List"%>

<div class="min-h-screen flex justify-center bg-gray-100 p-6 pt-10">
	<div class="bg-white p-8 rounded-xl shadow-lg w-full max-w-5xl">

		<!-- Header -->
		<div class="flex justify-between items-center mb-6 border-b pb-3">
			<h2 class="text-2xl font-bold text-gray-800">Crear Factura</h2>

			<!-- Botón Volver -->
			<a href="<%=request.getContextPath()%>/DashboardServlet?view=venta"
				class="bg-gray-200 hover:bg-gray-300 text-gray-800 px-4 py-2 rounded-md transition">
				Volver </a>
		</div>
		<%
		String error = request.getParameter("error");
		if ("stockInsuficiente".equals(error)) {
		%>
		<div
			class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
			Cantidad insuficiente en stock para uno o mas productos.</div>
		<%
		} else if ("sinProductos".equals(error)) {
		%>
		<div
			class="bg-yellow-100 border border-yellow-400 text-yellow-700 px-4 py-3 rounded mb-4">
			Debes agregar al menos un producto.</div>
		<%
		}
		%>
		<form id="formVenta" method="POST"
			action="${pageContext.request.contextPath}/ControladorVenta">
			<input type="hidden" name="accion" value="guardar">

			<!-- Cliente y Fecha -->
			<div class="flex justify-between items-end mb-8 gap-6">
				<div class="flex flex-col w-1/2">
					<label class="font-semibold mb-1 text-gray-700">Cliente</label> <select
						name="cliente"
						class="border border-gray-300 p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
						<%
						List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
						for (Cliente c : clientes) {
						%>
						<option value="<%=c.getId()%>"><%=c.getNombreNegocio()%></option>
						<%
						}
						%>
					</select>
				</div>

				<div class="flex flex-col">
					<label class="font-semibold mb-1 text-gray-700">Fecha</label> <input
						type="date" name="fecha" required
						class="border border-gray-300 p-2 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500">
				</div>
			</div>

			<!-- Tabla -->
			<div
				class="overflow-y-auto max-h-[300px] border border-gray-200 rounded-lg">
				<table class="min-w-full text-sm text-gray-700">
					<thead
						class="bg-gray-100 uppercase text-xs text-gray-600 sticky top-0">
						<tr>
							<th class="py-3 px-4 text-left">Producto</th>
							<th class="py-3 px-4 text-left">Cantidad</th>
							<th class="py-3 px-4 text-left">Precio</th>
							<th class="py-3 px-4 text-left">Total</th>
							<th class="py-3 px-4 text-center">Acciones</th>
						</tr>
					</thead>
					<tbody id="tbody" class="divide-y divide-gray-200">
						<!-- Filas dinámicas -->
					</tbody>
				</table>
			</div>

			<!-- Acciones -->
			<div class="mt-6 flex justify-between items-center">
				<button type="button" onclick="abrirModalAgregar()"
					class="bg-green-500 hover:bg-green-600 text-white px-4 py-2 rounded-md transition">
					+ Nueva fila</button>

				<button type="submit"
					class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-md transition">
					Guardar</button>
			</div>
		</form>
	</div>
</div>

<!-- Modal Agregar Libro -->
<div id="modalAgregarLibro"
	class="fixed inset-0 bg-black/50 backdrop-blur-sm hidden flex items-center justify-center">

	<div class="bg-white p-6 rounded-xl shadow-2xl w-full max-w-md">
		<h2 class="text-xl font-bold mb-4 text-gray-800">Agregar libro</h2>

		<label class="font-semibold text-gray-700">Producto</label> <select
			id="addProduct"
			class="w-full p-2 border border-gray-300 rounded-md mt-1 focus:outline-none focus:ring-2 focus:ring-blue-500"
			required>
			<%
			LibrosDAO dao = new LibrosDAO();
			List<Libros> lista = dao.listar();
			for (Libros l : lista) {
			%>
			<option value="<%=l.getIdLibro()%>" data-titulo="<%=l.getTitulo()%>"
				data-stock="<%=l.getStock()%>">
				<%=l.getTitulo()%> (Stock:
				<%=l.getStock()%>)
			</option>
			<%
			}
			%>
		</select> <label class="font-semibold mt-4 block text-gray-700">Cantidad</label>
		<input type="number" id="addCantidad" min="1" required
			class="w-full p-2 border border-gray-300 rounded-md mt-1 focus:outline-none focus:ring-2 focus:ring-blue-500">

		<label class="font-semibold mt-4 block text-gray-700">Precio</label> <input
			type="number" id="addPrecio" required
			class="w-full p-2 border border-gray-300 rounded-md mt-1 focus:outline-none focus:ring-2 focus:ring-blue-500">

		<div class="mt-6 flex justify-end gap-3">
			<button type="button" onclick="cerrarModal()"
				class="bg-gray-200 hover:bg-gray-300 px-4 py-2 rounded-md transition">
				Cancelar</button>
			<button type="button" onclick="agregarLibroALista()"
				class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md transition">
				Agregar</button>
		</div>
	</div>
</div>

<script src="./Js/Venta.js"></script>
