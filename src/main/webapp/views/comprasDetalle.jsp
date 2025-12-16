<%@ page import="java.util.List" %>
<%@ page import="modelo.CompraDetalle" %>

<table class="min-w-full text-sm border">
    <thead class="bg-gray-200">
        <tr>
            <th class="px-3 py-2">Libro</th>
            <th class="px-3 py-2">Cantidad</th>
            <th class="px-3 py-2">Precio</th>
            <th class="px-3 py-2">Total</th>
        </tr>
    </thead>
    <tbody>
<%
@SuppressWarnings("unchecked")
List<CompraDetalle> detalles = (List<CompraDetalle>) request.getAttribute("listaDetalle");

if (detalles != null) {
    for (CompraDetalle d : detalles) {
%>
<tr class="border-b text-center">
    <td class="px-3 py-2 "><%= d.getLibro().getTitulo() %></td>
    <td class="px-3 py-2 "><%= d.getCantidad() %></td>
    <td class="px-3 py-2 ">S/ <%= d.getPrecioUniCompra() %></td>
    <td class="px-3 py-2 ">
        S/ <%= d.getPrecioUniCompra().multiply(
                new java.math.BigDecimal(d.getCantidad())) %>
    </td>
</tr>
<%
    }
}
%>
    </tbody>
</table>