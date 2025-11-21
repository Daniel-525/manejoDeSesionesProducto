package controllers;
/**
 * Servlet principal que maneja la página de inicio
 * Muestra un contador de inicios de sesión y enlaces a otras funcionalidades
 * Autor: ITQ
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet({"/index.html", "/index"})
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer loginCounter = (Integer) getServletContext().getAttribute("loginCounter");
        if (loginCounter == null) loginCounter = 0;

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Inicio</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='contenedor'>");

            // --- ENCABEZADO ---
            out.println("    <div class='encabezado'>");
            out.println("        <h1 class='titulo'>Manejo de Cookies Http</h1>");
            out.println("        <span class='etiqueta'>Inicio</span>");
            out.println("    </div>");

            // --- CONTADOR ---
            out.println("<p class='mensaje'><strong>Contador de inicios de sesión: " + loginCounter + "</strong></p>");

            // --- LISTA DE OPCIONES ---
            out.println("<ul class='lista'>");

            out.println("    <li>");
            out.println("        <a class='boton' href='" + req.getContextPath() + "/productos'>Mostrar Productos</a>");
            out.println("    </li>");

            out.println("    <li>");
            out.println("        <a class='boton login' href='" + req.getContextPath() + "/login'>Login</a>");
            out.println("    </li>");

            out.println("    <li>");
            out.println("        <a class='boton logout' href='" + req.getContextPath() + "/logout'>Logout</a>");
            out.println("    </li>");

            out.println("    <li>");
            out.println("        <a class='boton secundario' href='" + req.getContextPath() + "/ver-carro'>Ver Carro</a>");
            out.println("    </li>");

            out.println("</ul>");

            out.println("</div>"); // Cierra contenedor
            out.println("</body>");
            out.println("</html>");
        }
    }
}
