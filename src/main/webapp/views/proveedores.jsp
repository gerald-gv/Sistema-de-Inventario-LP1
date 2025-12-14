<%@page import="modelo.Proveedor"%>
<%@page import="modeloDAO.ProveedorDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>

<%
    ProveedorDAO dao = new ProveedorDAO();
    List<Proveedor> lista = dao.listar();
%>

<div class="mb-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold text-gray-700">Gestión de Proveedores</h1>

    <div class="flex items-center gap-3">

        <input 
            type="text" 
            id="buscadorProveedores"
            placeholder="Buscar proveedor..."
            class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
            onkeyup="filtrarProveedores()"
        >

        <button onclick="abrirModalAgregar()"
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            + Agregar Proveedor
        </button>
    </div>
</div>

<div class="bg-white shadow-lg rounded-lg overflow-hidden">
    <table class="min-w-full text-left text-sm">
        <thead class="bg-gray-200 text-gray-700 uppercase text-xs">
            <tr>
                <th class="py-3 px-4">ID</th>
                <th class="py-3 px-4">Nombre</th>
                <th class="py-3 px-4">RUC</th>
                <th class="py-3 px-4">Telefono</th>
                <th class="py-3 px-4">Dirección</th>
                <th class="py-3 px-4">Email</th>
                <th class="py-3 px-4 text-center">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <% for(Proveedor p : lista){ %>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-2 px-4"><%= p.getId() %></td>
                <td class="py-2 px-4"><%= p.getNombre() %></td>
                <td class="py-2 px-4"><%= p.getRuc() %></td>
                <td class="py-2 px-4"><%= p.getTelefono() %></td>
                <td class="py-2 px-4"><%= p.getDireccion() %></td>
                <td class="py-2 px-4"><%= p.getEmail() %></td>

                <td class="py-2 px-4 text-center space-x-2">

                    <button 
                        onclick="abrirModalEditar(
                            '<%=p.getId()%>',
                            '<%=p.getNombre() %>',
                            '<%=p.getRuc() %>',
                            '<%=p.getTelefono()%>',
                            '<%=p.getDireccion()%>',
                            '<%=p.getEmail()%>'
                        )"
                        class="text-blue-600 hover:underline">
                        Editar
                    </button>

                    <a href="${pageContext.request.contextPath}/ControladorProveedor?accion=eliminar&id=<%=p.getId()%>"
                       onclick="return confirm('¿Eliminar proveedor?')"
                       class="text-red-600 hover:underline">
                       Eliminar
                    </a>

                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<div id="modalProveedor"
     class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center flex">

    <div class="bg-white p-6 rounded-lg w-96">

        <h2 id="modalTitulo" class="text-xl font-bold mb-4"></h2>

        <form id="formProveedor" method="POST" action="${pageContext.request.contextPath}/ControladorProveedor">

            <input type="hidden" name="accion" id="accion">
            <input type="hidden" name="id" id="id">

            <label class="font-semibold">Nombre</label>
            <input type="text" name="nombre" id="nombre" class="w-full p-2 border rounded mt-1 focus:outline-none" required>
			
			<label class="font-semibold mt-3 block">RUC</label>
            <input type="text" name="ruc" id="ruc" class="w-full p-2 border rounded mt-1 focus:outline-none" required>
			
			<label class="font-semibold">Telefono</label>
            <input type="text" name="telefono" id="telefono" class="w-full p-2 border rounded mt-1 focus:outline-none" required>
			
			<label class="font-semibold">Dirección</label>
            <input type="text" name="direccion" id="direccion" class="w-full p-2 border rounded mt-1 focus:outline-none" required>
			
            <label class="font-semibold mt-3 block">Email</label>
            <input type="email" name="email" id="email" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

    
            <div class="mt-5 flex justify-end gap-2">
                <button type="button" onclick="cerrarModal()"
                        class="bg-gray-300 px-4 py-2 rounded">
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
	    document.getElementById("modalTitulo").innerText = "Agregar Proveedor";
	    document.getElementById("accion").value = "guardar";
	    document.getElementById("id").value = "";
	    document.getElementById("formProveedor").reset();
	    document.getElementById("modalProveedor").classList.remove("hidden");
	}
	
	function abrirModalEditar(id, nombre, ruc, telefono, direccion, email){
	    document.getElementById("modalTitulo").innerText = "Editar Proveedor";
	    document.getElementById("accion").value = "actualizar";
	    document.getElementById("id").value = id;
	    document.getElementById("nombre").value = nombre;
	    document.getElementById("ruc").value = ruc;
	    document.getElementById("telefono").value = telefono;
	    document.getElementById("direccion").value = direccion;
	    document.getElementById("email").value = email;
	    document.getElementById("modalProveedor").classList.remove("hidden");
	}
	
	function cerrarModal(){
	    document.getElementById("modalProveedor").classList.add("hidden");
	}
	
	function filtrarProveedores() {
        const filtro = document.getElementById("buscadorProveedores").value.toLowerCase();
        const filas = document.querySelectorAll("tbody tr");

        filas.forEach(fila => {
            const nombre = fila.children[1].textContent.toLowerCase();
            const ruc = fila.children[2].textContent.toLowerCase();
            const telefono = fila.children[3].textContent.toLowerCase();
            const direccion = fila.children[4].textContent.toLowerCase();
            const email = fila.children[5].textContent.toLowerCase();

            if (
                nombre.includes(filtro) ||
                ruc.includes(filtro) ||
                telefono.includes(filtro) ||
                direccion.includes(filtro) ||
                email.includes(filtro)
            ) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    }
</script>