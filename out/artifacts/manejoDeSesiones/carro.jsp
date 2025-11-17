<%--
  Created by IntelliJ IDEA.
  User: crist
  Date: 13/11/2025
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %>

<%
    // Recuperar el carro de la sesión
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
%>

<html>
<head>
    <title>🛍️ Carro de Compras</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<div class="contenedor">
    <div class="encabezado">
        <h1 class="titulo">🛍️ Carro de Compras</h1>
        <span class="etiqueta">Tu pedido</span>
    </div>

    <%
        if (detalleCarro == null || detalleCarro.getItem().isEmpty()) {
    %>
    <p class="mensaje">Lo sentimos, no hay productos en el carro de compras. ¡Añade algo genial!</p>
    <div class="acciones">
        <a href="<%=request.getContextPath()%>/productos" class="boton secundario">SEGUIR COMPRANDO</a>
        <a href="<%=request.getContextPath()%>/index.html" class="boton secundario">Volver al Inicio</a>
    </div>
    <%
    } else {
    %>

    <table class="tabla-carro">
        <thead>
        <tr>
            <th># Producto</th>
            <th>Nombre</th>
            <th>Precio U.</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
            <th>IVA</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (ItemCarro item : detalleCarro.getItem()) {
        %>
        <tr>
            <td><%=item.getProducto().getId()%></td>
            <td><%=item.getProducto().getNombre()%></td>
            <td>$<%=String.format("%.2f", item.getProducto().getPrecio())%></td>
            <td><%=item.getCantidad()%></td>
            <td>$<%=String.format("%.2f", item.getSubtotal())%></td>
            <td>$<%=String.format("%.2f", item.getIva())%></td>
        </tr>
        <% } %>
        </tbody>

        <tfoot>
        <tr>
            <td colspan="4" class="total-label">Total a Pagar:</td>
            <td colspan="2" class="total-valor">$<%=String.format("%.2f", detalleCarro.getTotal())%></td>
        </tr>
        </tfoot>
    </table>

    <div class="acciones" style="justify-content: space-between;">
        <form action="<%=request.getContextPath()%>/descargarFactura" method="get" class="formulario-pdf">
            <input type="hidden" name="cliente" value="Cliente Prueba">
            <input type="hidden" name="total" value="<%=detalleCarro.getTotal()%>">
            <button type="submit" class="boton">Descargar Factura 📄</button>
        </form>

        <a href="<%=request.getContextPath()%>/productos" class="boton secundario">SEGUIR COMPRANDO</a>
        <a href="<%=request.getContextPath()%>/index.html" class="boton secundario">Volver</a>
    </div>

    <%
        }
    %>

</div>
</body>
</html>