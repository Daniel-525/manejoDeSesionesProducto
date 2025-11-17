/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Servlet que permite visualizar el contenido del carrito de compras.
 * Redirige al usuario a la página JSP que muestra los productos agregados al carrito.
 */

package controllers;
// Define el paquete donde se encuentra este servlet

// Importa clases de Jakarta Servlet para manejar solicitudes HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importa utilidades de Java
import java.io.IOException;

// Define la URL a la que responderá este servlet
@WebServlet("/carro")
public class VerCarroServlet extends HttpServlet {

    // Método que maneja solicitudes GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Redirige la solicitud a la página JSP "carro.jsp" para mostrar el contenido del carrito
        // forward() mantiene la URL original y pasa los atributos de request
        getServletContext().getRequestDispatcher("/carro.jsp").forward(req, resp);
    }
}
