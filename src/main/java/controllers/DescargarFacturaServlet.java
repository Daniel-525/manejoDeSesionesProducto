/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Servlet que genera y descarga una factura en PDF
 * con los productos agregados al carrito de compras.
 * Cada línea del código está comentada para explicar su funcionamiento.
 */

package controllers;

// Importa clases de Jakarta Servlet para manejar solicitudes HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importa clases de modelo para manejar los productos y el carrito
import models.DetalleCarro;
import models.ItemCarro;

// Importa clases de PDFBox para crear documentos PDF
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

// Importa clases de utilidades de Java
import java.io.IOException;

// Define la URL a la que responderá este servlet
@WebServlet("/descargarFactura")
public class DescargarFacturaServlet extends HttpServlet {

    // Método que maneja solicitudes GET al servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtiene el carrito de compras de la sesión del usuario
        DetalleCarro detalleCarro = (DetalleCarro) request.getSession().getAttribute("carro");

        // Verifica si el carrito está vacío o no existe
        if (detalleCarro == null || detalleCarro.getItem().isEmpty()) {
            // Si está vacío, muestra un mensaje y termina la ejecución
            response.getWriter().println("El carrito está vacío.");
            return;
        }

        // Configura la respuesta como un archivo PDF
        response.setContentType("application/pdf");
        // Configura el encabezado para forzar la descarga con el nombre "factura.pdf"
        response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");

        // Crea un documento PDF usando PDFBox dentro de un try-with-resources
        try (PDDocument doc = new PDDocument()) {

            // Crea una nueva página en blanco y la agrega al documento
            PDPage page = new PDPage();
            doc.addPage(page);

            // Crea un flujo de contenido para escribir en la página
            PDPageContentStream content = new PDPageContentStream(doc, page);

            // Variable para la posición vertical inicial del texto
            int y = 760;

            /* ===========================
               ENCABEZADO DE FACTURA
               =========================== */
            content.beginText(); // Inicia un bloque de texto
            content.setFont(PDType1Font.HELVETICA_BOLD, 22); // Define fuente y tamaño
            content.newLineAtOffset(200, y); // Posición horizontal y vertical
            content.showText("FACTURA"); // Escribe el título
            content.endText(); // Termina el bloque de texto

            y -= 30; // Ajusta la posición vertical para separar el encabezado

            // Dibuja una línea horizontal debajo del encabezado
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();

            y -= 40; // Ajusta la posición vertical para la tabla de productos

            /* ===========================
               ENCABEZADOS DE TABLA
               =========================== */
            // Columna "Producto"
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 12);
            content.newLineAtOffset(50, y);
            content.showText("Producto");
            content.endText();

            // Columna "Precio"
            content.beginText();
            content.newLineAtOffset(220, y);
            content.showText("Precio");
            content.endText();

            // Columna "Cantidad"
            content.beginText();
            content.newLineAtOffset(300, y);
            content.showText("Cantidad");
            content.endText();

            // Columna "Subtotal"
            content.beginText();
            content.newLineAtOffset(380, y);
            content.showText("Subtotal");
            content.endText();

            // Columna "IVA"
            content.beginText();
            content.newLineAtOffset(470, y);
            content.showText("IVA");
            content.endText();

            y -= 15; // Baja la posición vertical para separar de la línea

            // Dibuja línea debajo de los encabezados
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();

            y -= 20; // Ajusta posición para comenzar a listar productos

            /* ===========================
               FILAS DE PRODUCTOS
               =========================== */
            // Recorre cada item del carrito
            for (ItemCarro item : detalleCarro.getItem()) {

                // Escribe el nombre del producto
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, y);
                content.showText(item.getProducto().getNombre());
                content.endText();

                // Escribe el precio unitario del producto
                content.beginText();
                content.newLineAtOffset(220, y);
                content.showText(String.format("$%.2f", item.getProducto().getPrecio()));
                content.endText();

                // Escribe la cantidad comprada
                content.beginText();
                content.newLineAtOffset(300, y);
                content.showText(String.valueOf(item.getCantidad()));
                content.endText();

                // Escribe el subtotal (precio * cantidad)
                content.beginText();
                content.newLineAtOffset(380, y);
                content.showText(String.format("$%.2f", item.getSubtotal()));
                content.endText();

                // Escribe el IVA del producto
                content.beginText();
                content.newLineAtOffset(470, y);
                content.showText(String.format("$%.2f", item.getIva()));
                content.endText();

                y -= 20; // Baja la posición para la siguiente fila

                // Si se llega al final de la página, crea una nueva
                if (y < 100) {
                    content.close(); // Cierra la página actual
                    page = new PDPage(); // Crea nueva página
                    doc.addPage(page); // Agrega al documento
                    content = new PDPageContentStream(doc, page); // Crea flujo de contenido nuevo
                    y = 760; // Reinicia posición vertical
                }
            }

            y -= 20; // Ajusta posición antes de total final

            // Dibuja línea separadora antes del total
            content.moveTo(50, y);
            content.lineTo(550, y);
            content.stroke();

            y -= 30; // Ajusta posición vertical para total final

            /* ===========================
               TOTAL FINAL
               =========================== */
            content.beginText();
            content.setFont(PDType1Font.HELVETICA_BOLD, 18);
            content.newLineAtOffset(350, y); // Posición del total
            content.showText("TOTAL: $" + String.format("%.2f", detalleCarro.getTotal())); // Muestra total
            content.endText();

            // Cierra el flujo de contenido
            content.close();

            // Guarda el PDF generado en el flujo de salida de la respuesta HTTP
            doc.save(response.getOutputStream());
        }
    }
}
