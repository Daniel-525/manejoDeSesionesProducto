<%--
  Created by IntelliJ IDEA.
  User: ITQ
  Date: 10/11/2025
  Time: 10:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>

<div class="contenedor">

    <!-- ENCABEZADO -->
    <div class="encabezado">
        <h1 class="titulo">Iniciar Sesión</h1>
        <span class="etiqueta">Acceso</span>
    </div>

    <!-- FORMULARIO LOGIN -->
    <form action="login" method="post">

        <div class="campo">
            <label for="user">Usuario</label>
            <input type="text" id="user" name="user" required>
        </div>

        <div class="campo">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="acciones">
            <button type="submit" class="boton login">Entrar</button>
        </div>

    </form>

</div>

</body>
</html>
