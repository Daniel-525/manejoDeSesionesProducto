/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Servlet que permite agregar productos al carrito de compras.
 * Gestiona la sesión del usuario y almacena los productos seleccionados
 * en un objeto DetalleCarro dentro de la sesión.
 */

// Define el paquete donde se encuentra el servlet.
package controllers;

// Importa las clases necesarias de Jakarta Servlet para manejar solicitudes HTTP.
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Importa las clases de modelo utilizadas para representar los productos y el carrito.
import models.DetalleCarro;
import models.ItemCarro;
import models.Producto;

// Importa las clases de servicio que permiten acceder a los productos.
import services.ProductoService;
import services.ProductoServiceImplement;

// Importa clases de utilidades de Java.
import javax.swing.text.html.Option; // Nota: esta importación parece no ser necesaria.
import java.io.IOException;
import java.util.Optional;

// Define la URL a la que responderá este servlet.
@WebServlet("/agregar-carro")
public class AgregarCarroServlet extends HttpServlet {

    // Metodo que maneja solicitudes GET al servlet.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene el parametro "id" de la solicitud y lo convierte a Long.
        Long id = Long.parseLong(req.getParameter("id"));

        // Crea una instancia del servicio de productos para acceder a los datos.
        ProductoService service = new ProductoServiceImplement();

        // Busca el producto por su ID usando Optional para manejar la posible ausencia del producto.
        Optional<Producto> producto = service.porId(id);

        // Si el producto existe, se procede a agregarlo al carrito.
        if (producto.isPresent()) {

            // Crea un ItemCarro con cantidad 1 y el producto encontrado.
            ItemCarro item = new ItemCarro(1, producto.get());

            // Obtiene la sesión del usuario; si no existe, la crea.
            HttpSession session = req.getSession();

            // Variable para almacenar el carrito del usuario.
            DetalleCarro detalleCarro;

            // Si no hay carrito en la sesión, se crea uno nuevo y se guarda en la sesión.
            if (session.getAttribute("carro") == null){
                detalleCarro = new DetalleCarro();
                session.setAttribute("carro", detalleCarro);
            } else {
                // Si ya existe un carrito, se obtiene de la sesión.
                detalleCarro = (DetalleCarro) session.getAttribute("carro");
            }

            // Agrega el item al carrito (ya sea nuevo o existente).
            detalleCarro.addItemCarro(item);
        }

        // Redirige al usuario a la página del carrito para mostrar los productos agregados.
        resp.sendRedirect(req.getContextPath() + "/carro");
    }
}
