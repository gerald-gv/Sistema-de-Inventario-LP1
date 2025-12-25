<%@page import="modeloDAO.ProveedorDAO"%>
<%@page import="modeloDAO.LibrosDAO"%>
<%@page import="modelo.Libros"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Compras"%>
<%@page import="modelo.Proveedor"%>
<%@page import="modeloDAO.ComprasDAO"%>

<%
LibrosDAO dao = new LibrosDAO();
List<Libros> listaLibros = dao.listar();

ComprasDAO comprasDAO = new ComprasDAO();
List<Compras> listaCompras = comprasDAO.listar();

ProveedorDAO proveedorDAO = new ProveedorDAO();
List<Proveedor> listaProveedores = proveedorDAO.listar();
%>

<div class="mb-6 flex justify-between items-center">
	<h1 class="text-2xl font-bold text-gray-700">Gestión de Compras</h1>

	<div class="flex items-center gap-3">
		<input type="text" id="buscador" placeholder="Buscar compra..."
			class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
			onkeyup="filtrarTabla()">

		<button onclick="abrirModalAgregar()" class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
			+ Nueva Compra
		</button>
		<button onclick="generarExcelGeneral()" class="bg-green-600 text-white px-4 py-2 rounded-lg hover:bg-green-700">
			Generar Excel General
		</button>
			
	</div>
</div>

<div class="bg-white shadow-lg rounded-lg overflow-hidden">
	<table class="min-w-full text-left text-sm">
		<thead class="bg-gray-200 text-gray-700 uppercase text-xs">
			<tr>
				<th class="py-3 px-4">ID</th>
				<th class="py-3 px-4">Usuario</th>
				<th class="py-3 px-4">Proveedor</th>
				<th class="py-3 px-4">Fecha</th>
				<th class="py-3 px-4">Total</th>
				<th class="py-3 px-4 text-center w-40">Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Compras c : listaCompras) {
			%>
			<tr class="border-b hover:bg-gray-50">
				<td class="py-2 px-4"><%=c.getIdCompra()%></td>
				<td class="py-2 px-4">
    <%= c.getUsuario().getUsername() %>
</td>
				<td class="py-2 px-4"><%=c.getProveedor().getNombre()%></td>
				<td class="py-2 px-4"><%=c.getFecha()%></td>
				<td class="py-2 px-4">S/ <%=c.getTotalCompra()%></td>
				<td class="py-2 px-4">
					<div class="flex justify-center gap-2">
						<button onclick="verDetalle(<%=c.getIdCompra()%>)"
							class="w-28 bg-sky-600 hover:bg-sky-700 text-white py-2 rounded text-xs">
							Ver detalles</button>
						<button onclick="eliminarCompra(<%=c.getIdCompra()%>)"
							class="w-28 bg-red-600 hover:bg-red-700 text-white py-2 rounded text-xs">
							Eliminar</button>
					</div>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</div>

<!-- MODAL REGISTRAR COMPRA -->
<div id="modalCompra"
	class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center">

	<div class="bg-white p-6 rounded-lg w-[800px] flex gap-4">

		<!-- FORMULARIO IZQUIERDO -->
		<div class="flex-1">
			<h2 class="text-xl font-bold mb-4">Registrar Compra</h2>

			<form id="formCompra" method="POST"
				action="${pageContext.request.contextPath}/ControladorCompras">

				<input type="hidden" name="accion" value="guardar"> <label
					class="font-semibold block">Proveedor</label> <select
					name="proveedor"
					class="w-full p-2 border rounded mt-1 focus:outline-none" required>
					<option value="">Seleccione un proveedor</option>
					<%
					for (Proveedor p : listaProveedores) {
					%>
					<option value="<%=p.getId()%>">
						<%=p.getNombre()%>
					</option>
					<%
					}
					%>
				</select> <label class="font-semibold mt-3 block">Fecha</label> <input
					type="date" name="fecha"
					class="w-full p-2 border rounded mt-1 focus:outline-none" required>

				<hr class="my-4">

				<h3 class="font-semibold mb-2">Agregar Libro</h3>

				<label class="font-semibold block text-sm">Libro</label> <select
					id="selectLibro"
					class="w-full p-2 border rounded mt-1 focus:outline-none">
					<option value="">Seleccione un libro</option>
					<%
					for (Libros l : listaLibros) {
					%>
					<option value="<%=l.getIdLibro()%>"
						data-titulo="<%=l.getTitulo()%>">
						<%=l.getTitulo()%>
					</option>
					<%
					}
					%>
				</select> <label class="font-semibold mt-3 block text-sm">Cantidad</label> <input
					type="number" id="inputCantidad"
					class="w-full p-2 border rounded mt-1 focus:outline-none" min="1"
					value="1"> <label class="font-semibold mt-3 block text-sm">Precio
					Compra</label> <input type="number" id="inputPrecio"
					class="w-full p-2 border rounded mt-1 focus:outline-none"
					min="0.01" step="0.01">

				<button type="button" onclick="agregarLibroALista()"
					class="mt-3 w-full bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
					+ Agregar Libro</button>

				<div class="mt-5 flex justify-end gap-2">
					<button type="button" onclick="cerrarModalCompra()"
						class="bg-gray-300 px-4 py-2 rounded hover:bg-gray-400">
						Cancelar</button>

					<button type="submit"
						class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
						Guardar Compra</button>
				</div>
			</form>
		</div>

		<!-- LISTA DE LIBROS AGREGADOS (DERECHA DEL MODAL) -->
		<div class="w-64 bg-gray-50 p-4 rounded-lg">
			<h3 class="font-semibold mb-3 text-gray-700">Libros Agregados</h3>

			<div id="listaLibrosAgregados" class="space-y-2">
				<p class="text-gray-400 text-sm italic">Aún no hay libros
					agregados</p>
			</div>

			<div class="mt-4 pt-3 border-t">
				<p class="text-sm text-gray-600">
					Total de libros: <span id="totalLibros" class="font-semibold">0</span>
				</p>
			</div>
		</div>

	</div>
</div>

<!-- MODAL PARA DETALLES DE COMPRA-->
<div id="modalDetalle"
	class="fixed inset-0 bg-black bg-opacity-50 hidden flex items-center justify-center z-50">

	<div class="bg-white rounded-lg w-3/4 max-w-4xl p-6">
		<h2 class="text-xl font-semibold mb-4">Detalle de la compra</h2>

		<div id="contenidoDetalle">Cargando...</div>

		<div class="text-right mt-4">
			<!-- <button id="btnGenerarExcel" class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded">
		        Generar Excel
   			 </button> -->
		
			<button onclick="cerrarModalDetalle()"
				class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded">
				Cerrar</button>
		</div>
	</div>
</div>

<script>
// Array para almacenar los libros agregados
var librosAgregados = [];
var contadorLibros = 0;

function agregarLibroALista() {
    const selectLibro = document.getElementById('selectLibro');
    const inputCantidad = document.getElementById('inputCantidad');
    const inputPrecio = document.getElementById('inputPrecio');
    const selectProveedor = document.querySelector('select[name="proveedor"]');

    const idLibro = selectLibro.value;
    const titulo = selectLibro.options[selectLibro.selectedIndex].dataset.titulo;
    const cantidad = inputCantidad.value;
    const precio = inputPrecio.value;

    // Validaciones
    if (!idLibro) {
        alert('Debe seleccionar un libro');
        return;
    }
    if (!cantidad || cantidad < 1) {
        alert('La cantidad debe ser mayor a 0');
        return;
    }
    if (!precio || precio <= 0) {
        alert('El precio debe ser mayor a 0');
        return;
    }

    // VALIDAR QUE HAYA PROVEEDOR SELECCIONADO
    if (!selectProveedor.value) {
        alert('Debe seleccionar un proveedor antes de agregar libros');
        selectProveedor.focus();
        return;
    }

    // Agregar al array
    const libro = {
        id: contadorLibros++,
        idLibro: idLibro,
        titulo: titulo,
        cantidad: cantidad,
        precio: precio
    };

    librosAgregados.push(libro);
    
    // BLOQUEAR EL SELECTOR DE PROVEEDOR
    if (librosAgregados.length > 0) {
    	selectProveedor.style.pointerEvents = 'none';
        selectProveedor.classList.add('bg-gray-200', 'cursor-not-allowed');
    }
    
    actualizarVistaLibros();

    // Limpiar campos
    selectLibro.value = '';
    inputCantidad.value = '1';
    inputPrecio.value = '';
}

function eliminarLibro(id) {
    librosAgregados = librosAgregados.filter(function(libro) {
        return libro.id !== id;
    });
    
    // SI YA NO HAY LIBROS, DESBLOQUEAR PROVEEDOR
    if (librosAgregados.length === 0) {
        const selectProveedor = document.querySelector('select[name="proveedor"]');
        selectProveedor.disabled = false;
        selectProveedor.classList.remove('bg-gray-200', 'cursor-not-allowed');
    }
    
    actualizarVistaLibros();
}
//ESTA FUNCION OCURRE JUNTO AL MODAL DE LIBROS AGREGADOS
function actualizarVistaLibros() {
    const contenedor = document.getElementById('listaLibrosAgregados');
    const totalLibros = document.getElementById('totalLibros');

    if (librosAgregados.length === 0) {
        contenedor.innerHTML = '<p class="text-gray-400 text-sm italic">Aún no hay libros agregados</p>';
        totalLibros.textContent = '0';
        return;
    }

    var html = '';
    librosAgregados.forEach(function(libro) {
        html += '<div class="bg-white border border-gray-300 rounded-lg p-2 text-sm">' +
                '<div class="flex justify-between items-start mb-1">' +
                '<p class="font-semibold text-gray-800 text-xs leading-tight flex-1">' + libro.titulo + '</p>' +
                '<button onclick="eliminarLibro(' + libro.id + ')" ' +
                'class="text-red-500 hover:text-red-700 ml-2 text-lg leading-none">×</button>' +
                '</div>' +
                '<p class="text-gray-600 text-xs">Cant: ' + libro.cantidad + '</p>' +
                '<p class="text-gray-600 text-xs">Precio: S/ ' + parseFloat(libro.precio).toFixed(2) + '</p>' +
                '</div>';
    });

    contenedor.innerHTML = html;
    totalLibros.textContent = librosAgregados.length;
}

function abrirModalAgregar() {
    librosAgregados = [];
    contadorLibros = 0;
    actualizarVistaLibros();
    document.getElementById("modalCompra").classList.remove("hidden");
    document.getElementById("modalCompra").classList.add("flex");
}

function cerrarModalCompra() {
    document.getElementById("modalCompra").classList.add("hidden");
    document.getElementById("modalCompra").classList.remove("flex");
    document.getElementById('formCompra').reset();
    
    // DESBLOQUEAR EL SELECTOR DE PROVEEDOR
    const selectProveedor = document.querySelector('select[name="proveedor"]');
    selectProveedor.disabled = false;
    selectProveedor.classList.remove('bg-gray-200', 'cursor-not-allowed');
    
    librosAgregados = [];
    actualizarVistaLibros();
}

function verDetalle(idCompra) {
    fetch('<%=request.getContextPath()%>/ControladorCompras?accion=modalDetalle&id=' + idCompra)
        .then(function(response) {
            return response.text();
        })
        .then(function(html) {
            document.getElementById('contenidoDetalle').innerHTML = html;
            
            const btnExcel = document.getElementById("btnGenerarExcel");
            // Al escuhcar el evento, se ejecutara una funcion, a la cual llamara a la funcion generarExcel
            btnExcel.onclick = function(){
            	generarExcel(idCompra);
            } 
            
            const modal = document.getElementById('modalDetalle');
            modal.classList.remove('hidden');
        })
        .catch(function(err) {
            document.getElementById('contenidoDetalle').innerHTML =
                '<p class="text-red-500">Error al cargar el detalle</p>';
        });
}

function cerrarModalDetalle() {
    const modal = document.getElementById('modalDetalle');
    modal.classList.add('hidden');
}

function filtrarTabla() {
    const filtro = document.getElementById("buscador").value.toLowerCase();
    const filas = document.querySelectorAll("tbody tr");

    filas.forEach(function(fila) {
        const proveedor = fila.children[2].textContent.toLowerCase();
        fila.style.display = proveedor.includes(filtro) ? "" : "none";
    });
}

// Interceptar el envío del formulario
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('formCompra').addEventListener('submit', function(e) {
        if (librosAgregados.length === 0) {
            e.preventDefault();
            alert('Debe agregar al menos un libro');
            return;
        }

        // Agregar inputs hidden para cada libro
        var form = this;
        librosAgregados.forEach(function(libro) {
            // Input para ID del libro
            var inputLibro = document.createElement('input');
            inputLibro.type = 'hidden';
            inputLibro.name = 'libros[]';
            inputLibro.value = libro.idLibro;
            form.appendChild(inputLibro);

            // Input para cantidad
            var inputCantidad = document.createElement('input');
            inputCantidad.type = 'hidden';
            inputCantidad.name = 'cantidad[]';
            inputCantidad.value = libro.cantidad;
            form.appendChild(inputCantidad);

            // Input para precio
            var inputPrecio = document.createElement('input');
            inputPrecio.type = 'hidden';
            inputPrecio.name = 'precioCompra[]';
            inputPrecio.value = libro.precio;
            form.appendChild(inputPrecio);
        });
    });
});
//SCRIPT PARA ELIMINAR COMPRA -->
function eliminarCompra(id) {
    if (confirm("¿Seguro que deseas eliminar esta compra?\nEsta acción no se puede deshacer.")) {
        window.location.href =
            "ControladorCompras?accion=eliminar&id=" + id;
    }
}

// Generar Excel por compra

function generarExcel(idCompra) {
    // Redirigir al controlador ExportarCompra
	window.location.href = '<%=request.getContextPath()%>/ExportarCompra?id=' + idCompra;
}

// Generar Excel General
function generarExcelGeneral() {
    alert("Se está generando el Excel de compras, espere unos segundos...");

    window.location.href = '<%=request.getContextPath()%>/ExportarComprasGenerales';
}
</script>