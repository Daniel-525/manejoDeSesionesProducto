package controllers;
/*
 * Autor: Cristian Arias
 * Fecha y version: 10/11/2025 | Version: 1.0
 * Descripcion: Servlet encargado de cerrar la sesion del usuario autenticado.
 * Invalida la sesion actual y redirige al formulario de inicio de sesion.
 */

// Importa las clases necesarias para el manejo de servlets, sesiones y respuestas HTTP.
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Importa el servicio que gestiona la autenticacion de usuarios.
import services.LoginService;
import services.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

// Define la ruta URL donde este servlet respondera.
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // Crea una instancia del servicio de autenticacion.
    // Se utiliza para verificar si hay un usuario autenticado antes de cerrar la sesion.
    private final LoginService auth = new LoginServiceSessionImpl();

    // Metodo doGet: se ejecuta cuando el navegador realiza una peticion HTTP GET.
    // Su funcion principal es cerrar la sesion activa del usuario.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene el nombre del usuario autenticado, si existe, usando el servicio de sesion.
        Optional<String> userOpt = auth.getUsername(req);

        // Si existe un usuario autenticado, se procede a cerrar su sesion.
        if (userOpt.isPresent()) {
            // Recupera la sesion actual (sin crear una nueva si no existe).
            HttpSession session = req.getSession(false);

            // Si la sesion existe, la invalida, eliminando todos los datos asociados.
            if (session != null) session.invalidate();
        }

        // Redirige al usuario al formulario de inicio de sesion despues de cerrar la sesion.
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}
