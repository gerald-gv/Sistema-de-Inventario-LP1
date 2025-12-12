<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@page import="modeloDAO.UsuarioDAO"%>

<%
    UsuarioDAO dao = new UsuarioDAO();
    List<Usuario> lista = dao.listar();
%>

<div class="mb-6 flex justify-between items-center">
    <h1 class="text-2xl font-bold text-gray-700">Gestión de Usuarios</h1>

    <div class="flex items-center gap-3">

        <input 
            type="text" 
            id="buscadorUsuarios"
            placeholder="Buscar usuario..."
            class="px-3 py-2 border rounded-lg w-60 focus:outline-none"
            onkeyup="filtrarUsuarios()"
        >

        <button onclick="abrirModalAgregar()"
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700">
            + Agregar Usuario
        </button>
    </div>
</div>

<div class="bg-white shadow-lg rounded-lg overflow-hidden">
    <table class="min-w-full text-left text-sm">
        <thead class="bg-gray-200 text-gray-700 uppercase text-xs">
            <tr>
                <th class="py-3 px-4">ID</th>
                <th class="py-3 px-4">Usuario</th>
                <th class="py-3 px-4">Email</th>
                <th class="py-3 px-4">Rol</th>
                <th class="py-3 px-4 text-center">Acciones</th>
            </tr>
        </thead>
        <tbody>
            <% for(Usuario u : lista){ %>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-2 px-4"><%= u.getId() %></td>
                <td class="py-2 px-4"><%= u.getUsername() %></td>
                <td class="py-2 px-4"><%= u.getUserEmail() %></td>
                <td class="py-2 px-4"><%= u.getRol().getNombre() %></td>

                <td class="py-2 px-4 text-center space-x-2">

                    <button 
                        onclick="abrirModalEditar(
                            '<%=u.getId()%>',
                            '<%=u.getUsername()%>',
                            '<%=u.getUserEmail()%>',
                            '<%=u.getRol().getIdRol()%>'
                        )"
                        class="text-blue-600 hover:underline">
                        Editar
                    </button>

                    <a href="${pageContext.request.contextPath}/ControladorUsuario?accion=eliminar&id=<%=u.getId()%>"
                       onclick="return confirm('Â¿Eliminar usuario?')"
                       class="text-red-600 hover:underline">
                       Eliminar
                    </a>

                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<div id="modalUsuario"
     class="fixed inset-0 bg-black bg-opacity-40 hidden justify-center items-center flex">

    <div class="bg-white p-6 rounded-lg w-96">

        <h2 id="modalTitulo" class="text-xl font-bold mb-4"></h2>

        <form id="formUsuario" method="POST" action="${pageContext.request.contextPath}/ControladorUsuario">

            <input type="hidden" name="accion" id="accion">
            <input type="hidden" name="id" id="id">

            <label class="font-semibold">Nombre</label>
            <input type="text" name="username" id="username" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Email</label>
            <input type="email" name="email" id="email" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Password</label>
            <input type="password" name="password" id="password" class="w-full p-2 border rounded mt-1 focus:outline-none" required>

            <label class="font-semibold mt-3 block">Rol</label>
            <select name="rol" id="rol" class="w-full p-2 border rounded mt-1 cursor-pointer">
                <option value="1">Administrador</option>
                <option value="2">Empleado</option>
            </select>

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
	    document.getElementById("modalTitulo").innerText = "Agregar Usuario";
	    document.getElementById("accion").value = "guardar";
	    document.getElementById("id").value = "";
	    document.getElementById("formUsuario").reset();
	    document.getElementById("rol").value = "1";
	    document.getElementById("modalUsuario").classList.remove("hidden");
	}
	
	function abrirModalEditar(id, username, email, rol){
	    document.getElementById("modalTitulo").innerText = "Editar Usuario";
	    document.getElementById("accion").value = "actualizar";
	    document.getElementById("id").value = id;
	    document.getElementById("username").value = username;
	    document.getElementById("email").value = email;
	    document.getElementById("password").value = "";
	    document.getElementById("rol").value = rol;
	    document.getElementById("modalUsuario").classList.remove("hidden");
	}
	
	function cerrarModal(){
	    document.getElementById("modalUsuario").classList.add("hidden");
	}
	
	function filtrarUsuarios() {
        const filtro = document.getElementById("buscadorUsuarios").value.toLowerCase();
        const filas = document.querySelectorAll("tbody tr");

        filas.forEach(fila => {
            const username = fila.children[1].textContent.toLowerCase();
            const email = fila.children[2].textContent.toLowerCase();
            const rol = fila.children[3].textContent.toLowerCase();

            if (
                username.includes(filtro) ||
                email.includes(filtro) ||
                rol.includes(filtro)
            ) {
                fila.style.display = "";
            } else {
                fila.style.display = "none";
            }
        });
    }
</script>