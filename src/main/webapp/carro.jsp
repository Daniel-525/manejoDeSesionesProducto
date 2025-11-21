<%--
  Created by IntelliJ IDEA.
  User: ITQ
  Date: 13/11/2025
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.DetalleCarro" %>
<%@ page import="models.ItemCarro" %>
<%@ page import="java.text.DecimalFormat" %>

<%
    DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");
    DecimalFormat df = new DecimalFormat("#,##0.00");
%>

<html>
<head>
    <title>Carro de Compras</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<div class="contenedor">

    <!-- ENCABEZADO -->
    <div class="encabezado">
        <h1 class="titulo">Carro de Compras</h1>
        <span class="etiqueta">Compra</span>
    </div>

    <!-- MENSAJE SI NO HAY PRODUCTOS -->
    <%
        if (detalleCarro == null || detalleCarro.getItems().isEmpty()) {
    %>
    <p class="mensaje">Lo sentimos, no hay productos en el carro de compras</p>

    <% } else { %>

    <!-- TABLA DEL CARRO -->
    <table class="tabla-carro">
        <tr>
            <th>ID Producto</th>
            <th>Nombre</th>
            <th>Precio Unitario</th>
            <th>Cantidad</th>
            <th>Subtotal</th>
            <th>IVA (15%)</th>
            <th>Total</th>
        </tr>

        <% for (ItemCarro item : detalleCarro.getItems()) { %>
        <tr>
            <td><%=item.getProducto().getIdProducto()%></td>
            <td><%=item.getProducto().getNombre()%></td>
            <td>$<%=df.format(item.getProducto().getPrecio())%></td>
            <td><%=item.getCantidad()%></td>
            <td>$<%=df.format(item.getSubTotal())%></td>
            <td>$<%=df.format(item.getIva())%></td>
            <td>$<%=df.format(item.getTotalConIva())%></td>
        </tr>
        <% } %>

        <!-- TOTALES -->
        <tr>
            <td colspan="4" class="total-label">Totales:</td>
            <td class="total-valor">$<%=df.format(detalleCarro.getTotal())%></td>
            <td class="total-valor">$<%=df.format(detalleCarro.getTotalIva())%></td>
            <td class="total-valor">$<%=df.format(detalleCarro.getTotalConIva())%></td>
        </tr>
    </table>

    <% } %>

    <!-- ACCIONES -->
    <div class="acciones">

        <a href="<%=request.getContextPath()%>/productos" class="boton">
            Seguir Comprando
        </a>

        <% if (detalleCarro != null && !detalleCarro.getItems().isEmpty()) { %>
        <a href="<%=request.getContextPath()%>/generar-factura" class="boton secundario">
            Imprimir Factura
        </a>
        <% } %>

        <a href="<%=request.getContextPath()%>/index.html" class="boton secundario">
            Volver al Inicio
        </a>

    </div>

</div>

</body>
</html>
