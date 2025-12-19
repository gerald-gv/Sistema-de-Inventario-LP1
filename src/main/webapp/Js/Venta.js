let librosAgregados = [];

function abrirModalAgregar() {
    document.getElementById("addProduct").value = "";
    document.getElementById("addCantidad").value = "";
    document.getElementById("addPrecio").value = "";
    document.getElementById("modalAgregarLibro").classList.remove("hidden");
}

function cerrarModal() {
    document.getElementById("modalAgregarLibro").classList.add("hidden");
}

// CORRECCIÓN: Cambié el nombre de la función
function agregarLibroALista() {
    const selectLibro = document.getElementById("addProduct");
    const cantidadInput = document.getElementById("addCantidad");
    const precioInput = document.getElementById("addPrecio");

    const idLibro = selectLibro.value;
    const titulo = selectLibro.options[selectLibro.selectedIndex].dataset.titulo;
    const cantidad = cantidadInput.value;
    const precio = precioInput.value;

    // Validación mejorada
    if (!idLibro || !cantidad || !precio) {
        alert("Por favor complete todos los campos");
        return;
    }

    if (parseInt(cantidad) <= 0) {
        alert("La cantidad debe ser mayor a 0");
        return;
    }

    if (parseFloat(precio) <= 0) {
        alert("El precio debe ser mayor a 0");
        return;
    }

    // Verificar si el libro ya fue agregado
    const yaExiste = librosAgregados.some(l => l.idLibro === idLibro);
    if (yaExiste) {
        alert("Este libro ya fue agregado. Para modificarlo, elimínelo y vuelva a agregarlo.");
        return;
    }

    librosAgregados.push({ idLibro, titulo, cantidad, precio });
    addRow(idLibro, titulo, cantidad, precio);

    cerrarModal();
}

function addRow(idLibro, producto, cantidad, precio) {
    const tbody = document.getElementById("tbody");
    const fila = document.createElement("tr");
    fila.classList.add("border-b", "hover:bg-gray-50");
    fila.setAttribute("data-id-libro", idLibro); // Para identificar la fila

    const total = (parseFloat(cantidad) * parseFloat(precio)).toFixed(2);

    fila.innerHTML = `
        <td class="py-2 px-4">
            ${producto}
            <input type="hidden" name="idLibro[]" value="${idLibro}">
        </td>
        <td class="py-2 px-4">
            ${cantidad}
            <input type="hidden" name="cantidad[]" value="${cantidad}">
        </td>
        <td class="py-2 px-4">
            S/ ${parseFloat(precio).toFixed(2)}
            <input type="hidden" name="precio[]" value="${precio}">
        </td>
        <td class="py-2 px-4">S/ ${total}</td>
        <td class="py-2 px-4 text-center">
            <button type="button" class="text-red-600 hover:underline eliminar">Eliminar</button>
        </td>
    `;

    tbody.appendChild(fila);

    fila.querySelector(".eliminar").addEventListener("click", () => {
        tbody.removeChild(fila);
        librosAgregados = librosAgregados.filter(l => l.idLibro !== idLibro);
        console.log("Libro eliminado. Libros restantes:", librosAgregados);
    });

    console.log("Libro agregado:", { idLibro, producto, cantidad, precio, total });
}

// Validar antes de enviar el formulario
document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('formVenta');
    if (form) {
        form.addEventListener('submit', function(e) {
            const tbody = document.getElementById('tbody');
            
            if (tbody.children.length === 0 || librosAgregados.length === 0) {
                e.preventDefault();
                alert('Debe agregar al menos un producto a la venta');
                return false;
            }

            console.log("Formulario enviado con los siguientes datos:");
            console.log("Cliente:", form.querySelector('[name="cliente"]').value);
            console.log("Fecha:", form.querySelector('[name="fecha"]').value);
            console.log("Libros:", librosAgregados);
            
            // Verificar que los inputs hidden existen
            const idsLibros = form.querySelectorAll('[name="idLibro[]"]');
            const cantidades = form.querySelectorAll('[name="cantidad[]"]');
            const precios = form.querySelectorAll('[name="precio[]"]');
            
            console.log("IDs de libros a enviar:", Array.from(idsLibros).map(i => i.value));
            console.log("Cantidades a enviar:", Array.from(cantidades).map(i => i.value));
            console.log("Precios a enviar:", Array.from(precios).map(i => i.value));
        });
    }
});