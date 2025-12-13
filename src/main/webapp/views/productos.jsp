<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Libros"%>
<%@page import="modelo.Categoria"%>
<%@page import="modeloDAO.LibrosDAO"%>
<%@page import="modeloDAO.CategoriaDAO"%>

<%
LibrosDAO libroDAO = new LibrosDAO();
List<Libros> listaLibros = libroDAO.listar();

CategoriaDAO categoriaDAO = new CategoriaDAO();
List<Categoria> listaCategorias = categoriaDAO.listar();
%>

<div class="mb-6 flex justify-between items-center">
	<h1 class="text-2xl font-bold text-gray-700">Gestión de Libros</h1>

	<div class="flex items-center gap-3">
		<input type="text" id="buscador" placeholder="Buscar libro..."
			class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
			onkeyup="filtrarTabla()">

		<button onclick="abrirModalAgregar()"
			class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
			+ Agregar Libro</button>
	</div>
</div>

<div class="bg-white shadow-lg rounded-lg overflow-hidden">
	<table class="min-w-full text-left text-sm">
		<thead class="bg-gray-200 text-gray-700 uppercase text-xs">
			<tr>
				<th class="py-3 px-4">ID</th>
				<th class="py-3 px-4">Título</th>
				<th class="py-3 px-4">Autor</th>
				<th class="py-3 px-4">Categoría</th>
				<th class="py-3 px-4">Descripción</th>
				<th class="py-2 px-2">Precio de Compra</th>
				<th class="py-2 px-2">Precio de Venta</th>
				<th class="py-2 px-2">Stock</th>

				<th class="py-3 px-4 text-center w-40">Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Libros l : listaLibros) {
			%>
			<tr class="border-b hover:bg-gray-50">
				<td class="py-2 px-4"><%=l.getIdLibro()%></td>
				<td class="py-2 px-4"><%=l.getTitulo()%></td>
				<td class="py-2 px-4"><%=l.getAutor()%></td>
				<td class="py-2 px-4"><%=l.getIdCat().getNombre()%></td>
				<td class="py-2 px-4 text-center"> <!-- Boton para mostrar descripcion -->
					<%
					if (l.getDescripcion() != null && !l.getDescripcion().isEmpty()) {
					%>   <!-- Si la descripcion no esta vacia se muestra el boton -->
					<button
						onclick="verDescripcion(`<%=l.getDescripcion().replace("`", "\\`")%>`)"
						class="text-blue-600 hover:underline">Ver descripción</button> <%
 					} else { 
 					%> <span class="text-gray-400 italic">Sin descripción</span> <%
 					}
 					%><!-- Si la descripcion esta vacia se muestra el else ps XDXD -->
				</td>
				<td class="py-2 px-4">S/ <%=l.getPrecioCompra()%></td>
				<td class="py-2 px-4">S/ <%=l.getPrecioVenta()%></td>
				<td class="py-2 px-4"><%=l.getStock()%></td>
				<td class="py-2 px-4 text-center space-x-2">
					<!-- Boton y codigo de editar, no te pierdas -->
				<button
    				onclick="abrirModalEditar(
        				'<%= l.getIdLibro() %>',
        				'<%= l.getTitulo().replace("'", "\\'") %>',
        				'<%= l.getAutor().replace("'", "\\'") %>',
        				'<%= l.getDescripcion().replace("'", "\\'").replace("\n", "\\n") %>', 
        				<!-- El replace esta ahi para que no se rompa el codigo en caso de meter comillas en la descripcion -->
        				'<%= l.getIdCat().getIdCategoria() %>',
        				'<%= l.getPrecioCompra() %>',
        				'<%= l.getPrecioVenta() %>',
        				'<%= l.getStock() %>'
    				)"
    				class="text-blue-600 hover:underline">
    				Editar
				</button> <!-- Este es el boton eliminar -->
					<a
					href="${pageContext.request.contextPath}/ControladorLibro?accion=eliminar&id=<%=l.getIdLibro()%>"
					onclick="return confirm('¿Eliminar libro?')"
					class="text-red-600 hover:underline"> Eliminar </a>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</div>

<!-- Este modal se ejecuta al hacer click en "AGREGAR LIBRO" y "EDITAR" viva el reciclaje -->
<div id="modalLibro"
	class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center flex">

	<div class="bg-white p-6 rounded-lg w-96">

		<h2 id="modalTitulo" class="text-xl font-bold mb-4"></h2>

		<form id="formLibro" method="POST"
      action="${pageContext.request.contextPath}/ControladorLibro">

    <input type="hidden" name="accion" id="accion">
    <input type="hidden" name="id" id="id">

    <label class="font-semibold">Título</label>
    <input type="text" name="titulo" id="titulo"
           class="w-full p-2 border rounded mt-1 focus:outline-none" required>

    <label class="font-semibold mt-3 block">Autor</label>
    <input type="text" name="autor" id="autor"
           class="w-full p-2 border rounded mt-1 focus:outline-none" required>

    <label class="font-semibold mt-3 block">Descripción</label>
    <textarea name="descripcion" id="descripcion"
              class="w-full p-2 border rounded mt-1 focus:outline-none"
              rows="3"></textarea>

    <label class="font-semibold mt-3 block">Categoría</label>
    <select name="categoria" id="categoria"
            class="w-full p-2 border rounded mt-1 focus:outline-none" required>
        <option value="">Seleccione</option>
        <% for (Categoria c : listaCategorias) { %>
            <option value="<%= c.getIdCategoria() %>">
                <%= c.getNombre() %>
            </option>
        <% } %>
    </select>

    <div class="grid grid-cols-2 gap-3 mt-3">
        <div>
            <label class="font-semibold">Precio Compra</label>
            <input type="number" name="precioCompra" id="precioCompra"
                   step="0.01" min="0"
                   class="w-full p-2 border rounded mt-1 focus:outline-none" required>
        </div>

        <div>
            <label class="font-semibold">Precio Venta</label>
            <input type="number" name="precioVenta" id="precioVenta"
                   step="0.01" min="0"
                   class="w-full p-2 border rounded mt-1 focus:outline-none" required>
        </div>
    </div>

    <label class="font-semibold mt-3 block">Stock</label>
    <input type="number" name="stock" id="stock"
           min="0"
           class="w-full p-2 border rounded mt-1 focus:outline-none" required>

    <div class="mt-5 flex justify-end gap-2">
        <button type="button" onclick="cerrarModal()"
                class="bg-gray-300 px-4 py-2 rounded">
            Cancelar
        </button>

        <button type="submit"
                class="bg-blue-600 text-white px-4 py-2 rounded">
            Guardar
        </button>
    </div>

</form>
	</div>

</div>
<div id="modalDescripcion"
	class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center flex">

	<div
		class="bg-white p-6 rounded-lg w-[500px] max-h-[80vh] overflow-y-auto">

		<h2 class="text-xl font-bold mb-4">Descripción del libro</h2>

		<p id="textoDescripcion" class="text-gray-700 whitespace-pre-wrap"></p>

		<div class="mt-5 text-right">
			<button onclick="cerrarDescripcion()"
				class="bg-blue-600 text-white px-4 py-2 rounded">Cerrar</button>
		</div>

	</div>
</div>
<script> //SCRIPT SEPARADO PARA NO CONFUNDIRME, SOLO SE ENCARGA DE MOSTRAR LA DESCRIPCION
    function verDescripcion(texto) {
        document.getElementById("textoDescripcion").innerText = texto;
        document.getElementById("modalDescripcion").classList.remove("hidden");
    }

    function cerrarDescripcion() {
        document.getElementById("modalDescripcion").classList.add("hidden");
    }
</script>
<script>
    function abrirModalAgregar(){
        document.getElementById("modalTitulo").innerText = "Agregar Libro";
        document.getElementById("accion").value = "guardar";
        document.getElementById("id").value = "";
        document.getElementById("formLibro").reset();
        document.getElementById("modalLibro").classList.remove("hidden");
    }
    
    function abrirModalEditar(id, titulo, autor, descripcion, idCategoria, precioCompra, precioVenta, stock) {
        document.getElementById("modalTitulo").innerText = "Editar Libro";
        document.getElementById("accion").value = "actualizar";
        document.getElementById("id").value = id;
        document.getElementById("titulo").value = titulo;
        document.getElementById("autor").value = autor;
        document.getElementById("descripcion").value = descripcion;
        document.getElementById("categoria").value = idCategoria;
        document.getElementById("precioCompra").value = precioCompra;
        document.getElementById("precioVenta").value = precioVenta;
        document.getElementById("stock").value = stock;
        document.getElementById("modalLibro").classList.remove("hidden");
    }
    
    function cerrarModal(){
        document.getElementById("modalLibro").classList.add("hidden");
    }
    
    function filtrarTabla() {
        const filtro = document.getElementById("buscador").value.toLowerCase();
        const filas = document.querySelectorAll("tbody tr");

        filas.forEach(fila => {
            const titulo = fila.children[1].textContent.toLowerCase();
            const autor = fila.children[2].textContent.toLowerCase();
            const categoria = fila.children[3].textContent.toLowerCase();

            fila.style.display =
                titulo.includes(filtro) ||
                autor.includes(filtro) ||
                categoria.includes(filtro)
                ? "" : "none";
        });
    }
</script>
