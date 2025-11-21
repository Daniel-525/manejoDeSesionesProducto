package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import services.LoginService;
import services.LoginServiceSessionImpl;
import services.ProductoServices;
import services.ProductosServicesImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductoServices service = new ProductosServicesImpl();
        List<Producto> productos = service.listar();

        LoginService auth = new LoginServiceSessionImpl();
        Optional<String> usernameOptional = auth.getUsername(req);

        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Listado de Productos</title>");
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div class='contenedor'>");

            // ENCABEZADO
            out.println("<div class='encabezado'>");
            out.println("<h1 class='titulo'>Listado de Productos</h1>");
            out.println("<span class='etiqueta'>INVENTARIO</span>");
            out.println("</div>");

            // MENSAJE DE BIENVENIDA
            usernameOptional.ifPresent(username ->
                    out.println("<p class='bienvenida'>Bienvenido <strong>" + username + "</strong></p>")
            );

            // TABLA
            out.println("<table class='tabla'>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");
            out.println("<th>Precio</th>");
            out.println("<th>Stock</th>");
            out.println("<th>Descripción</th>");
            out.println("<th>F. Caducidad</th>");
            out.println("<th>F. Elaboración</th>");
            out.println("<th>Opciones</th>");
            out.println("</tr>");
            out.println("</thead>");

            out.println("<tbody>");

            for (Producto p : productos) {
                out.println("<tr>");
                out.println("<td>" + p.getIdProducto() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                out.println("<td>$" + p.getPrecio() + "</td>");
                out.println("<td>" + p.getStock() + "</td>");
                out.println("<td>" + p.getDescripcion() + "</td>");
                out.println("<td>" + p.getFechaCaducidad() + "</td>");
                out.println("<td>" + p.getFechaElaboracion() + "</td>");

                out.println("<td>");
                out.println("<a href='" + req.getContextPath()
                        + "/agregar-carro?id=" + p.getIdProducto()
                        + "' class='boton secundario'>Agregar</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</tbody>");
            out.println("</table>");

            out.println("</div>"); // contenedor

            out.println("</body>");
            out.println("</html>");
        }
    }
}
