package controlador;

import org.apache.pdfbox.pdmodel.*;
import java.awt.Color;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DetalleVenta;
import modelo.Factura;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts; //PARA LAS FUENTES
import java.io.IOException;
import java.util.List;

public class FacturaPDF {

	public static void generar(Factura factura, HttpServletResponse response) throws IOException {
	    PDDocument document = new PDDocument();
	    PDPage page = new PDPage(PDRectangle.A4);
	    document.addPage(page);
	    PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
	    PDType1Font fontRegular = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

	    PDPageContentStream content = new PDPageContentStream(document, page);

	    // ===== FONDO DE CABECERA =====
	    content.setNonStrokingColor(new Color(230, 230, 250)); 
	    content.addRect(0, 750, page.getMediaBox().getWidth(), 80);
	    content.fill();

	    // ===== TÍTULO =====
	    content.beginText();
	    content.setNonStrokingColor(Color.BLACK);
	    content.setFont(fontBold, 24);
	    content.newLineAtOffset(50, 770);
	    content.showText("FACTURA");
	    content.endText();

	    // ===== N° FACTURA Y FECHA =====
	    content.beginText();
	    content.setNonStrokingColor(Color.BLACK);
	    content.setFont(fontBold, 12);
	    content.newLineAtOffset(400, 770);
	    content.showText("N° " + factura.getId());
	    content.endText();

	    content.beginText();
	    content.setNonStrokingColor(Color.BLACK);
	    content.setFont(fontRegular, 12);
	    content.newLineAtOffset(400, 755);
	    content.showText("Fecha: " + factura.getFecha());
	    content.endText();

	    // ===== DATOS DEL CLIENTE Y VENDEDOR =====
	    float yStart = 710; 
	    content.setStrokingColor(Color.BLACK);
	    content.setLineWidth(0.5f);
	    content.addRect(45, yStart - 70, 500, 80);
	    content.stroke();

	    content.beginText();
	    content.setFont(fontRegular, 11);
	    content.setNonStrokingColor(Color.BLACK);
	    content.newLineAtOffset(50, yStart);
	    content.showText("Vendedor: " + factura.getUsuario().getUsername());
	    content.newLineAtOffset(0, -15);
	    content.showText("Cliente: " + factura.getCliente().getNombreNegocio());
	    content.newLineAtOffset(0, -15);
	    content.showText("Tipo de cliente: " + factura.getCliente().getTipoCliente());
	    content.newLineAtOffset(0, -15);
	    content.showText("Email: " + factura.getCliente().getEmail());
	    content.newLineAtOffset(0, -15);
	    content.showText("Teléfono: " + factura.getCliente().getContacto());
	    content.endText();

	    // ===== ENCABEZADO DE DETALLES =====
	    float y = yStart - 100;
	    content.setNonStrokingColor(200f/255, 200f/255, 200f/255);
	    content.addRect(50, y, 500, 20);
	    content.fill();

	    content.beginText();
	    content.setNonStrokingColor(0, 0, 0);
	    content.setFont(fontRegular, 11);
	    content.newLineAtOffset(50, y + 5);
	    content.showText("Libro");
	    content.newLineAtOffset(250, 0);
	    content.showText("Cant.");
	    content.newLineAtOffset(60, 0);
	    content.showText("Precio");
	    content.newLineAtOffset(70, 0);
	    content.showText("Subtotal");
	    content.endText();

	    // ===== DETALLES =====
	    y -= 20;
	    double total = 0;
	    List<DetalleVenta> listaDetalles = factura.getDetalles();
	    if (listaDetalles != null && !listaDetalles.isEmpty()) {
	        for (DetalleVenta d : listaDetalles) {
	            content.beginText();
	            content.setFont(fontRegular, 10);
	            content.newLineAtOffset(50, y);
	            content.showText(d.getTituloLibro());
	            content.endText();

	            content.beginText();
	            content.newLineAtOffset(300, y);
	            content.showText(String.valueOf(d.getCantidad()));
	            content.endText();

	            content.beginText();
	            content.newLineAtOffset(360, y);
	            content.showText(String.format("S/ %.2f", d.getPrecioUnitario()));
	            content.endText();

	            content.beginText();
	            content.newLineAtOffset(430, y);
	            content.showText(String.format("S/ %.2f", d.getSubtotal()));
	            content.endText();

	            y -= 15;
	            total += d.getSubtotal();
	        }
	    }

	    // ===== TOTAL =====
	    y -= 10;
	    content.setNonStrokingColor(230f/255, 230f/255, 250f/255);
	    content.addRect(380, y, 170, 20);
	    content.fill();

	    content.beginText();
	    content.setFont(fontRegular, 12);
	    content.setNonStrokingColor(0, 0, 0);
	    content.newLineAtOffset(385, y + 5);
	    content.showText("TOTAL: S/ " + String.format("%.2f", total));
	    content.endText();

	    content.close();

	    // ===== RESPUESTA =====
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "inline; filename=factura_" + factura.getId() + ".pdf");
	    document.save(response.getOutputStream());
	    document.close();
	}

}
