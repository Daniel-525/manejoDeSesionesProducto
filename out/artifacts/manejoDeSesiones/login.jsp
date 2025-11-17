<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>🔑 Formulario de Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<div class="contenedor">
    <div class="encabezado">
        <h1 class="titulo">Iniciar sesión</h1>
        <span class="etiqueta">Acceso al sistema</span>
    </div>

    <form action="/manejoDeSesiones/login" method="post">
        <div class="campo">
            <label for="username">Username</label>
            <div>
                <input type="text" name="username" id="username" placeholder="Ingrese su usuario" required>
            </div>
        </div>

        <div class="campo">
            <label for="password">Password</label>
            <div>
                <input type="password" name="password" id="password" placeholder="********" required>
            </div>
        </div>

        <div class="acciones" style="justify-content: flex-end;">
            <input type="submit" value="Login" class="boton">
        </div>
    </form>

</div>
</body>
</html>