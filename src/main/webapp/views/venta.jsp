<%@page import="modelo.DetalleVenta"%>
<%@page import="modelo.Factura"%>
<%@page import="modeloDAO.FacturaDAO"%>
<%@page import="modelo.DetalleVenta"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<%
FacturaDAO dao = new FacturaDAO();
List<Factura> lista = dao.listar();
%>

<!-- Pagina -->
<div class="mb-6 flex justify-between items-center">
	<!-- Titulo -->
	<h1 class="text-2xl font-bold text-gray-700">Facturas</h1>

	<!-- Buscador -->
	<div class="flex items-center gap-3">
		<input type="text" id="buscadorFacturas"
			placeholder="Buscar factura..."
			class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
			onkeyup="filtrarFacturas()">
		<!-- Generar nueva factura -->
		<a href="<%=request.getContextPath()%>/ControladorVenta"
			class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">+
			Generar nueva factura</a>
	</div>
</div>

<!-- Tabla -->
<div class="bg-white shadow-lg rounded-lg overflow-hidden">
	<table id="tablaFacturas" class="min-w-full text-left text-sm">
		<thead class="bg-gray-200 text-gray-700 uppercase text-xs">
			<tr>
				<th class="py-3 px-4">ID</th>
				<th class="py-3 px-4">Fecha</th>
				<th class="py-3 px-4">Cliente</th>
				<th class="py-3 px-4">Monto Total</th>
				<th class="py-3 px-4 text-center">Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
			if (lista != null && !lista.isEmpty()) {
			%>
			<%
			for (Factura f : lista) {
			%>
			<%
			// Obtener los detalles de esta factura
			List<DetalleVenta> detalles = dao.obtenerDetallesFactura(f.getId());
			%>
			<tr class="border-b hover:bg-gray-50">
				<td class="py-2 px-4"><%=f.getId()%></td>
				<td class="py-2 px-4"><%=f.getFecha()%></td>
				<td class="py-2 px-4"><%=f.getCliente().getNombreNegocio()%></td>
				<td class="py-2 px-4">S/ <%=String.format("%.2f", f.getTotal())%></td>

				<td class="py-2 px-4 text-center space-x-2">
					<button
						onclick="abrirModalVer(
    <%=f.getId()%>,
    '<%=f.getFecha()%>',
    '<%=f.getCliente().getNombreNegocio()%>',
    '<%= f.getCliente().getTipoCliente() != null 
        ? f.getCliente().getTipoCliente().replace("'", "\\'")
        : "No definido" %>',
    '<%=f.getCliente().getEmail() != null ? f.getCliente().getEmail() : "No registrado"%>',
    '<%=f.getCliente().getContacto() != null ? f.getCliente().getContacto() : "No registrado"%>',
    <%=f.getTotal()%>,
    [
        <% for (int i = 0; i < detalles.size(); i++) {
            DetalleVenta d = detalles.get(i); %>
        {
            titulo: '<%=d.getTituloLibro() != null ? d.getTituloLibro().replace("'", "\\'") : ""%>',
            autor: '<%=d.getAutor() != null ? d.getAutor().replace("'", "\\'") : ""%>',
            cantidad: <%=d.getCantidad()%>,
            precio: <%=d.getPrecioUnitario()%>,
            subtotal: <%=d.getSubtotal()%>
        }<%= i < detalles.size() - 1 ? "," : "" %>
        <% } %>
    ]
)"
						class="text-blue-600 hover:underline">Ver Detalle</button> <a
					href="${pageContext.request.contextPath}/ControladorVenta?accion=eliminar&id=<%= f.getId() %>"
					onclick="return confirm('¿Está seguro de eliminar esta factura?')"
					class="text-red-600 hover:underline"> Eliminar </a> <a
					href="${pageContext.request.contextPath}/ControladorVenta?accion=generarPDF&id=<%= f.getId() %>"
					class="text-green-600 hover:underline" target="_blank"> Generar
						PDF </a>
				</td>
			</tr>
			<%
			}
			%>
			<%
			} else {
			%>
			<tr>
				<td colspan="5" class="py-4 px-4 text-center text-gray-500">No
					hay facturas registradas</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</div>

<!-- Modal para Ver Detalle de Factura -->
<div id="modalFactura"
	class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center z-50">
	<div
		class="bg-white p-6 rounded-lg w-[800px] max-h-[90vh] overflow-y-auto">
		<div class="flex justify-between items-center mb-4">
			<h2 id="modalTitulo" class="text-2xl font-bold text-gray-800">Detalle
				de Factura</h2>
			<button onclick="cerrarModalFactura()"
				class="text-gray-500 hover:text-gray-700 text-2xl">&times;</button> <!-- EL &TIMES ES PARA LA X DE CERRAR -->
		</div>

		<!-- Información de la Factura -->
		<div class="bg-gray-50 p-4 rounded-lg mb-4">
			<div class="grid grid-cols-2 gap-4">
				<div>
					<label class="font-semibold text-gray-700 block mb-1">ID
						Factura</label>
					<p id="facturaIdDisplay" class="text-lg"></p>
				</div>

				<div>
					<label class="font-semibold text-gray-700 block mb-1">Fecha</label>
					<p id="facturaFecha" class="text-lg"></p>
				</div>

				<div class="col-span-2">
					<label class="font-semibold text-gray-700 block mb-1">Cliente</label>
					<div class="flex items-center gap-3">
						<p id="facturaCliente" class="text-lg font-medium"></p>

						<!-- Tipo de cliente -->
						<span id="facturaClienteTipo"
							class="px-2 py-1 text-xs rounded-full bg-blue-100 text-blue-700 font-semibold">
						</span>
					</div>
				</div>

				<div>
					<label class="font-semibold text-gray-700 block mb-1">Email</label>
					<p id="facturaClienteEmail" class="text-gray-600"></p>
				</div>

				<div>
					<label class="font-semibold text-gray-700 block mb-1">Teléfono</label>
					<p id="facturaClienteTelefono" class="text-gray-600"></p>
				</div>
			</div>
		</div>

		<!-- Lista de Productos Vendidos -->
		<div class="mb-4">
			<h3 class="text-lg font-bold text-gray-800 mb-3">Productos
				Vendidos</h3>
			<div class="overflow-x-auto">
				<table class="min-w-full text-left text-sm border">
					<thead class="bg-gray-100 text-gray-700 uppercase text-xs">
						<tr>
							<th class="py-2 px-3 border-b">Libro</th>
							<th class="py-2 px-3 border-b">Autor</th>
							<th class="py-2 px-3 border-b text-center">Cantidad</th>
							<th class="py-2 px-3 border-b">Precio Unit.</th>
							<th class="py-2 px-3 border-b">Subtotal</th>
						</tr>
					</thead>
					<tbody id="tablaDetalles">
						<!-- Se llenará dinámicamente -->
					</tbody>
				</table>
			</div>
		</div>

		<!-- Total -->
		<div class="bg-blue-50 p-4 rounded-lg border-2 border-blue-200">
			<div class="flex justify-between items-center">
				<span class="text-xl font-bold text-gray-800">TOTAL:</span> <span
					id="facturaTotal" class="text-2xl font-bold text-blue-600"></span>
			</div>
		</div>

		<!-- Botón Cerrar -->
		<div class="mt-6 flex justify-end">
			<button type="button" onclick="cerrarModalFactura()"
				class="bg-gray-500 text-white px-6 py-2 rounded hover:bg-gray-600">
				Cerrar</button>
		</div>
	</div>
</div>

<script type="text/javascript">
// Definir las funciones directamente en window
window.abrirModalVer = function(id, fecha, cliente, tipoCliente, email, telefono, total, detalles) {
	console.log({
	    id,
	    fecha,
	    cliente,
	    tipoCliente,
	    email,
	    telefono,
	    total
	});
    console.log("Abriendo modal para factura ID:", id);
    console.log("Detalles:", detalles);
    
    document.getElementById("modalTitulo").innerText = "Detalle de Factura #" + id;
    document.getElementById("facturaIdDisplay").innerText = "#" + id;
    document.getElementById("facturaFecha").innerText = fecha;
    document.getElementById("facturaCliente").innerText = cliente;
    document.getElementById("facturaClienteTipo").innerText = tipoCliente;
    document.getElementById("facturaClienteEmail").innerText = email;
    document.getElementById("facturaClienteTelefono").innerText = telefono;
    document.getElementById("facturaTotal").innerText = "S/ " + parseFloat(total).toFixed(2);
    
    var tbody = document.getElementById("tablaDetalles");
    tbody.innerHTML = "";
    
    //PARA CAMBIAR DE COLOR SEGUN EL CLIENTE
    var tipoSpan = document.getElementById("facturaClienteTipo");
    tipoSpan.innerText = tipoCliente;

    tipoSpan.className =
        "px-4 py-3 text-xs rounded-full font-semibold " +
        (tipoCliente === "Emprendedor"
            ? "bg-green-100 text-green-700"
            : "bg-blue-100 text-blue-700");
    
    if (detalles && detalles.length > 0) {
        for(var i = 0; i < detalles.length; i++) {
            var detalle = detalles[i];
            var fila = document.createElement("tr");
            fila.className = "border-b hover:bg-gray-50";
            fila.innerHTML = 
                '<td class="py-2 px-3">' + detalle.titulo + '</td>' +
                '<td class="py-2 px-3">' + detalle.autor + '</td>' +
                '<td class="py-2 px-3 text-center">' + detalle.cantidad + '</td>' +
                '<td class="py-2 px-3">S/ ' + parseFloat(detalle.precio).toFixed(2) + '</td>' +
                '<td class="py-2 px-3 font-semibold">S/ ' + parseFloat(detalle.subtotal).toFixed(2) + '</td>';
            tbody.appendChild(fila);
        }
    }
    
    document.getElementById("modalFactura").classList.remove("hidden");
    document.getElementById("modalFactura").classList.add("flex");
};

window.cerrarModalFactura = function() {
    document.getElementById("modalFactura").classList.add("hidden");
    document.getElementById("modalFactura").classList.remove("flex");
};

window.filtrarFacturas = function() {
    var filtro = document.getElementById("buscadorFacturas").value.toLowerCase();
    var tabla = document.getElementById("tablaFacturas");
    var tbody = tabla.getElementsByTagName("tbody")[0];
    if (!tbody) return;
    var filas = tbody.getElementsByTagName("tr");
    for (var i = 0; i < filas.length; i++) {
        var fila = filas[i];
        var texto = fila.textContent.toLowerCase();
        fila.style.display = texto.includes(filtro) ? "" : "none";
    }
};
</script>