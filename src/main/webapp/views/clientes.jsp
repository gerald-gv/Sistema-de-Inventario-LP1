<%@page import="modelo.Cliente"%>
<%@page import="java.util.List"%>
<%@page import="modeloDAO.ClienteDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
ClienteDAO clienteDAO = new ClienteDAO();
List<Cliente> listaClientes = clienteDAO.listar();
%>

<div class="mb-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold text-gray-700">Gestión de Clientes</h1>

    <div class="flex items-center gap-3">
        <input type="text" id="buscador" placeholder="Buscar Cliente..."
               class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
               onkeyup="filtrarTabla()">

        <button onclick="abrirModalAgregar()"
                class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            + Agregar Cliente
        </button>
    </div>
</div>

<section class="bg-white shadow-lg rounded-lg overflow-hidden">
    <table class="min-w-full text-left text-sm">
        <thead class="bg-gray-200 text-gray-700 uppercase text-xs">
            <tr>
                <th class="py-3 px-4">ID</th>
                <th class="px-3">Nombre - Negocio</th>
                <th class="px-2">Email</th>
                <th class="px-2">Contacto</th>
                <th class="px-2">Dirección</th>
                <th class="px-2">Tipo - Cliente</th>
                <th class="px-4 text-center">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <% for(Cliente cli : listaClientes){ %>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-2 px-4"><%=cli.getId() %></td>
                <td class="px-3"><%=cli.getNombreNegocio() %></td>
                <td class="px-2"><%=cli.getEmail() %></td>
                <td class="px-2"><%=cli.getContacto() %></td>
                <td class="px-2"><%=cli.getDireccion() %></td>
                <td class="px-2"><%=cli.getTipoCliente() %></td>
                <td class="px-4 text-center space-x-2">
                    <button 
                        onclick="abrirModalEditar(
                            '<%=cli.getId()%>',
                            '<%=cli.getNombreNegocio()%>',
                            '<%=cli.getEmail()%>',
                            '<%=cli.getContacto()%>',
                            '<%=cli.getDireccion()%>',
                            '<%=cli.getTipoCliente()%>'
                        )"
                        class="text-blue-600 hover:underline">
                        Editar
                    </button>

                    <a href="${pageContext.request.contextPath}/ControladorCliente?accion=eliminar&id=<%=cli.getId()%>"
                       onclick="return confirm('¿Eliminar cliente?')"
                       class="text-red-600 hover:underline">
                        Eliminar
                    </a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</section>

<!-- MODAL -->
<div id="modalCliente"
     class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center flex">

    <div class="bg-white p-6 rounded-lg w-96">

        <h2 id="modalTitulo" class="text-xl font-bold mb-4"></h2>

        <form id="formCliente" method="POST" action="${pageContext.request.contextPath}/ControladorCliente">

            <input type="hidden" name="accion" id="accion">
            <input type="hidden" name="id" id="id">

            <label class="font-semibold">Nombre - Negocio</label>
            <input type="text" name="nombreNegocio" id="nombreNegocio" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Email</label>
            <input type="email" name="email" id="email" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Contacto</label>
            <input type="text" name="contacto" id="contacto" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Dirección</label>
            <input type="text" name="direccion" id="direccion" class="w-full p-2 border rounded mt-1 focus:outline-none">

            <label class="font-semibold mt-3 block">Tipo Cliente</label>
            <input type="text" name="tipoCliente" id="tipoCliente" class="w-full p-2 border rounded mt-1 focus:outline-none">

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
    document.getElementById("modalTitulo").innerText = "Agregar Cliente";
    document.getElementById("accion").value = "guardar";
    document.getElementById("id").value = "";
    document.getElementById("formCliente").reset();
    document.getElementById("modalCliente").classList.remove("hidden");
}

function abrirModalEditar(id, nombreNegocio, email, contacto, direccion, tipoCliente){
    document.getElementById("modalTitulo").innerText = "Editar Cliente";
    document.getElementById("accion").value = "actualizar";
    document.getElementById("id").value = id;
    document.getElementById("nombreNegocio").value = nombreNegocio;
    document.getElementById("email").value = email;
    document.getElementById("contacto").value = contacto;
    document.getElementById("direccion").value = direccion;
    document.getElementById("tipoCliente").value = tipoCliente;
    document.getElementById("modalCliente").classList.remove("hidden");
}

function cerrarModal(){
    document.getElementById("modalCliente").classList.add("hidden");
}

function filtrarTabla() {
    const filtro = document.getElementById("buscador").value.toLowerCase();
    const filas = document.querySelectorAll("tbody tr");

    filas.forEach(fila => {
        const nombre = fila.children[1].textContent.toLowerCase();
        const email = fila.children[2].textContent.toLowerCase();

        if (nombre.includes(filtro) || email.includes(filtro)) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
}
</script>
