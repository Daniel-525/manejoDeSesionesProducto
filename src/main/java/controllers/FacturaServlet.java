package controllers;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.DetalleCarro;
import models.ItemCarro;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/generar-factura")
public class FacturaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DetalleCarro detalleCarro = (DetalleCarro) session.getAttribute("carro");

        if (detalleCarro == null || detalleCarro.getItems().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/ver-carro");
            return;
        }

        // Configurar respuesta para PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"factura_" +
                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf\"");

        try {
            // Generar el documento PDF
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Fuentes - usar constantes directas
            PdfFont fontBold = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);
            PdfFont fontNormal = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);

            // Encabezado de la factura
            Paragraph titulo = new Paragraph("FACTURA DE COMPRA")
                    .setFont(fontBold)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(titulo);

            // Fecha de la factura
            Paragraph fecha = new Paragraph("Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()))
                    .setFont(fontNormal)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginBottom(20);
            document.add(fecha);

            // Tabla de productos
            float[] columnWidths = {2, 3, 2, 2, 2, 2, 3};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            // Encabezado de la tabla
            String[] headers = {"ID", "Producto", "Precio Unit.", "Cantidad", "Subtotal", "IVA 15%", "Total"};
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setFont(fontBold).setFontSize(9)));
            }

            // Datos de cada producto
            for (ItemCarro item : detalleCarro.getItems()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getProducto().getIdProducto())).setFont(fontNormal).setFontSize(8)));
                table.addCell(new Cell().add(new Paragraph(item.getProducto().getNombre()).setFont(fontNormal).setFontSize(8)));
                table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", item.getProducto().getPrecio())).setFont(fontNormal).setFontSize(8)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getCantidad())).setFont(fontNormal).setFontSize(8)));
                table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", item.getSubTotal())).setFont(fontNormal).setFontSize(8)));
                table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", item.getIva())).setFont(fontNormal).setFontSize(8)));
                table.addCell(new Cell().add(new Paragraph(String.format("$%.2f", item.getTotalConIva())).setFont(fontNormal).setFontSize(8)));
            }

            document.add(table);

            // Totales
            Paragraph subtotal = new Paragraph(String.format("Subtotal: $%.2f", detalleCarro.getTotal()))
                    .setFont(fontBold)
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(10);
            document.add(subtotal);

            Paragraph iva = new Paragraph(String.format("IVA (15%%): $%.2f", detalleCarro.getTotalIva()))
                    .setFont(fontBold)
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(iva);

            Paragraph total = new Paragraph(String.format("TOTAL: $%.2f", detalleCarro.getTotalConIva()))
                    .setFont(fontBold)
                    .setFontSize(13)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginBottom(20);
            document.add(total);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al generar la factura");
        }
    }
}
