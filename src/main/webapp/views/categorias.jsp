<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Categoria"%>
<%@page import="modeloDAO.CategoriaDAO"%>

<%
    CategoriaDAO dao = new CategoriaDAO();
    List<Categoria> lista = dao.listar();
%>

<div class="mb-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold text-gray-700">Gestión de Categorías</h1>

    <div class="flex items-center gap-3">
        <!-- BUSCADOR -->
        <input 
            type="text" 
            id="buscador"
            placeholder="Buscar categoría..."
            class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
            onkeyup="filtrarTabla()"
        >

        <button onclick="abrirModalAgregar()"
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            + Agregar Categoría
        </button>
    </div>
</div>

<div class="bg-white shadow-lg rounded-lg overflow-hidden">
    <table class="min-w-full text-left text-sm">
        <thead class="bg-gray-200 text-gray-700 uppercase text-xs">
            <tr>
                <th class="py-3 px-4">ID</th>
                <th class="py-3 px-4">Nombre</th>
                <th class="py-3 px-4">Descripción</th>
                <th class="py-3 px-4 text-center">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <% for(Categoria c : lista){ %>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-2 px-4"><%= c.getIdCategoria() %></td>
                <td class="py-2 px-4"><%= c.getNombre() %></td>
                <td class="py-2 px-4"><%= c.getDescripcion() %></td>

                <td class="py-2 px-4 text-center space-x-2">

                    <!-- BOTÃN EDITAR -->
                    <button 
                        onclick="abrirModalEditar(
                            '<%=c.getIdCategoria()%>',
                            '<%=c.getNombre()%>',
                            '<%=c.getDescripcion()%>'
                        )"
                        class="text-blue-600 hover:underline">
                        Editar
                    </button>

                    <!-- BOTÃN ELIMINAR -->
                    <a href="${pageContext.request.contextPath}/ControladorCategoria?accion=eliminar&id=<%=c.getIdCategoria()%>"
                       onclick="return confirm('Â¿Eliminar categorÃ­a?')"
                       class="text-red-600 hover:underline">
                        Eliminar
                    </a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<!-- MODAL -->
<div id="modalCategoria"
     class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center flex">

    <div class="bg-white p-6 rounded-lg w-96">

        <h2 id="modalTitulo" class="text-xl font-bold mb-4"></h2>

        <form id="formCategoria" method="POST" action="${pageContext.request.contextPath}/ControladorCategoria">

            <input type="hidden" name="accion" id="accion">
            <input type="hidden" name="id" id="id">

            <label class="font-semibold">Nombre</label>
            <input type="text" name="nombre" id="nombre" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Descripción</label>
            <textarea name="descripcion" id="descripcion" class="w-full p-2 border rounded mt-1 focus:outline-none"></textarea>

            <div class="mt-5 flex justify-end gap-2">
                <button type="button" onclick="cerrarModal()" class="bg-gray-300 px-4 py-2 rounded">
                    Cancelar
                </button>

                <button class="bg-blue-600 text-white px-4 py-2 rounded">
                    Guardar
                </button>
            </div>

        </form>
    </div>

</div>

<script>
    function abrirModalAgregar(){
        document.getElementById("modalTitulo").innerText = "Agregar Categoría";
        document.getElementById("accion").value = "guardar";
        document.getElementById("id").value = "";
        document.getElementById("formCategoria").reset();
        document.getElementById("modalCategoria").classList.remove("hidden");
    }
    
    function abrirModalEditar(id, nombre, descripcion){
        document.getElementById("modalTitulo").innerText = "Editar Categoría";
        document.getElementById("accion").value = "actualizar";
        document.getElementById("id").value = id;
        document.getElementById("nombre").value = nombre;
        document.getElementById("descripcion").value = descripcion;
        document.getElementById("modalCategoria").classList.remove("hidden");
    }
    
    function cerrarModal(){
        document.getElementById("modalCategoria").classList.add("hidden");
    }
    
    function filtrarTabla() {
        const filtro = document.getElementById("buscador").value.toLowerCase();
        const filas = document.querySelectorAll("tbody tr");

        filas.forEach(fila => {
            const nombre = fila.children[1].textContent.toLowerCase();
            const descripcion = fila.children[2].textContent.toLowerCase();

            if (nombre.includes(filtro) || descripcion.includes(filtro)) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    }
</script>