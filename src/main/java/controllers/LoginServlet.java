package controllers;
/**
 * Servlet para manejar el proceso de autenticación de usuarios
 * Controla el login/logout y mantiene un contador de sesiones activas
 * Autor: ITQ
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.LoginService;
import services.LoginServiceSessionImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    private int loginCounter = 0;

    private LoginServiceSessionImpl authService = new LoginServiceSessionImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> usernameOptional = authService.getUsername(req);

        if (usernameOptional.isPresent()) {
            // Usuario ya autenticado → Mostrar mensaje con diseño moderno
            resp.setContentType("text/html;charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {

                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Bienvenido</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
                out.println("</head>");
                out.println("<body>");

                out.println("<div class='contenedor'>");

                // ENCABEZADO
                out.println("<div class='encabezado'>");
                out.println("<h1 class='titulo'>Bienvenido</h1>");
                out.println("<span class='etiqueta'>Sesión activa</span>");
                out.println("</div>");

                // MENSAJE
                out.println("<p class='mensaje'>Hola <strong>" + usernameOptional.get() + "</strong>, has iniciado sesión con éxito.</p>");

                // ACCIONES
                out.println("<div class='acciones'>");
                out.println("<a href='" + req.getContextPath() + "/index.html' class='boton secundario'>Volver</a>");
                out.println("<a href='" + req.getContextPath() + "/logout' class='boton logout'>Cerrar Sesión</a>");
                out.println("</div>");

                out.println("</div>"); // fin contenedor

                out.println("</body>");
                out.println("</html>");
            }

        } else {
            // Si no hay sesión → mostrar JSP de login
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("user");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            loginCounter++;
            getServletContext().setAttribute("loginCounter", loginCounter);

            resp.sendRedirect(req.getContextPath() + "/productos");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos, no está autorizado para ingresar a esta página!");
        }
    }

    public static int getLoginCounter(HttpServletRequest req) {
        Integer counter = (Integer) req.getServletContext().getAttribute("loginCounter");
        return counter != null ? counter : 0;
    }
}
