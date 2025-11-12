package controllers;
/*
 * Autor: Cristian Arias
 * Fecha y version: 10/11/2025 | Version: 1.0
 * Descripcion: Servlet encargado de manejar el proceso de inicio de sesion.
 * Valida las credenciales del usuario, administra la sesion mediante cookies
 * y registra la cantidad de veces que un usuario ha ingresado al sistema.
 */

// Importa las clases necesarias para trabajar con servlets y manejo de sesiones HTTP.
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

// Importa los servicios relacionados con la autenticacion de usuarios.
import services.LoginService;
import services.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

// Define las rutas URL donde este servlet respondera.
@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {

    // Crea una instancia del servicio de autenticacion.
    // Se encarga de validar el usuario y la contrasena.
    private final LoginService auth = new LoginServiceSessionImpl();

    // Metodo doGet: se ejecuta cuando el navegador realiza una peticion HTTP GET.
    // Muestra el formulario de inicio de sesion o redirige al listado de productos si el usuario ya esta autenticado.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene el nombre del usuario autenticado (si existe) usando el servicio de sesion.
        Optional<String> userOpt = auth.getUsername(req);

        // Si el usuario ya esta autenticado, redirige directamente a la pagina de productos.
        if (userOpt.isPresent()) {
            resp.sendRedirect(req.getContextPath() + "/productos");
        }
        // Si no hay usuario autenticado, muestra la pagina JSP de login.
        else {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Metodo doPost: se ejecuta cuando el usuario envia el formulario de inicio de sesion.
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene los valores ingresados por el usuario en el formulario.
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Verifica si las credenciales ingresadas son validas.
        if (auth.autenticar(username, password)) {

            // Crea o recupera una sesion activa para el usuario autenticado.
            HttpSession session = req.getSession(true);

            // Guarda el nombre de usuario dentro de la sesion.
            session.setAttribute("username", username);

            // Define el tiempo maximo de inactividad de la sesion (15 minutos).
            session.setMaxInactiveInterval(15 * 60);

            // Recupera el mapa global donde se almacenan las veces que cada usuario ha iniciado sesion.
            Map<String, Integer> counts = (Map<String, Integer>) getServletContext().getAttribute("loginCounts");

            // Si el mapa existe y el nombre de usuario no es nulo, incrementa el contador de ingresos.
            if (counts != null && username != null) {
                counts.merge(username, 1, Integer::sum);
            }

            // Redirige al usuario autenticado a la pagina de productos.
            resp.sendRedirect(req.getContextPath() + "/productos");
        }
        // Si las credenciales no son validas, muestra nuevamente la pagina de login con un mensaje de error.
        else {
            req.setAttribute("error", "Credenciales invalidas.");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
