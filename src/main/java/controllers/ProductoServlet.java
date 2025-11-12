package controllers;
/*
 * Autor: Cristian Arias
 * Fecha y version: 10/11/2025 | Version: 1.0
 * Descripcion: Servlet que muestra una pagina HTML con el listado de productos.
 * Si el usuario ha iniciado sesion, se muestran los precios y el numero de veces que ha ingresado.
 */

// Importa las clases necesarias de Jakarta para manejar peticiones HTTP y servlets.
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importa las clases del modelo y los servicios utilizados.
import models.Producto;
import services.LoginServiceSessionImpl;
import services.ProductoService;
import services.ProductoServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Define las rutas URL que este servlet atendera.
@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    // Crea una instancia del servicio de autenticacion de usuario.
    private final services.LoginService auth = new LoginServiceSessionImpl();

    // Crea una instancia del servicio de productos.
    private final ProductoService service = new ProductoServiceImpl();

    // Metodo doGet: se ejecuta cuando el navegador realiza una peticion HTTP GET.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Evita el almacenamiento en cache del contenido en el navegador.
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        // Obtiene el nombre de usuario de la sesion actual, si existe.
        Optional<String> userOpt = auth.getUsername(req);

        // Obtiene la lista de productos desde el servicio.
        List<Producto> productos = service.listar();

        // Establece el tipo de contenido de la respuesta como HTML con codificacion UTF-8.
        resp.setContentType("text/html;charset=UTF-8");

        // Escribe la respuesta HTML que se enviara al navegador.
        try (PrintWriter out = resp.getWriter()) {

            // Estructura inicial del documento HTML.
            out.println("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Productos</title>");
            out.println("<link rel='stylesheet' href='styles.css'>");
            out.println("</head><body><div class='container'>");

            // Seccion de cabecera con el titulo y la informacion de sesion.
            out.println("<div class='header'>");
            out.println("<h2>Listado de Productos</h2>");

            // Si el usuario ha iniciado sesion, se muestra su nombre y numero de ingresos.
            if (userOpt.isPresent()) {
                String user = userOpt.get();

                // Recupera el mapa global que contiene los conteos de inicio de sesion.
                Map<String, Integer> counts = (Map<String, Integer>) getServletContext().getAttribute("loginCounts");

                // Obtiene el numero de veces que el usuario ha iniciado sesion.
                int veces = counts != null ? counts.getOrDefault(user, 0) : 0;

                // Muestra el nombre del usuario, cantidad de ingresos y enlace para cerrar sesion.
                out.println("<div><span class='badge'>" + user + "</span> &nbsp;|&nbsp; Inicios: <b>" + veces + "</b> &nbsp;|&nbsp; <a class='button secondary' href='" + req.getContextPath() + "/logout'>Cerrar sesion</a></div>");
            } else {
                // Si no hay sesion activa, muestra enlace para ir al login.
                out.println("<div>No autenticado · <a class='button secondary' href='" + req.getContextPath() + "/login.html'>Login</a></div>");
            }
            out.println("</div>");

            // Construye la tabla HTML con los datos de los productos.
            out.println("<table><thead><tr><th>Id</th><th>Nombre</th><th>Tipo</th>");
            if (userOpt.isPresent()) out.println("<th>Precio</th>");
            out.println("</tr></thead><tbody>");

            // Recorre la lista de productos y genera una fila por cada uno.
            for (Producto p : productos) {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                if (userOpt.isPresent()) out.println("<td>$" + p.getPrecio() + ".00</td>");
                out.println("</tr>");
            }

            // Cierra la tabla.
            out.println("</tbody></table>");

            // Agrega un enlace para regresar a la pagina de inicio.
            out.println("<div class='actions'><a class='button secondary' href='" + req.getContextPath() + "/index.html'>Inicio</a></div>");

            // Cierra las etiquetas del documento HTML.
            out.println("</div></body></html>");
        }
    }
}
