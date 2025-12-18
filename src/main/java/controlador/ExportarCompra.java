package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.CompraDetalle;
import modelo.Compras;
import modeloDAO.CompraDetalleDAO;
import modeloDAO.ComprasDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/ExportarCompra")
public class ExportarCompra extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ComprasDAO comprasDAO = new ComprasDAO();
    private CompraDetalleDAO detalleDAO = new CompraDetalleDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int idCompra = Integer.parseInt(request.getParameter("id"));

        Compras compra = comprasDAO.list(idCompra);
        List<CompraDetalle> detalles = detalleDAO.listarPorCompra(idCompra);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Compra_" + idCompra);

        
        // Columnas
         
        int COL_D = 3;
        int COL_E = 4;
        int COL_F = 5;
        int COL_G = 6;
        
        
        // Ancho de columnas
        
        sheet.setColumnWidth(COL_D, 18 * 256);
        sheet.setColumnWidth(COL_E, 25 * 256); 
        sheet.setColumnWidth(COL_F, 20 * 256);
        sheet.setColumnWidth(COL_G, 18 * 256);
        sheet.setHorizontallyCenter(true);
        
        // Fuentes
        
        Font normalFont = workbook.createFont();
        normalFont.setFontHeightInPoints((short) 14);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 15);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        
        
        // Estilo info general 
        
        CellStyle infoStyle = workbook.createCellStyle();
        infoStyle.setFont(normalFont);
        infoStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        infoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        infoStyle.setBorderTop(BorderStyle.THIN);
        infoStyle.setBorderBottom(BorderStyle.THIN);
        infoStyle.setBorderLeft(BorderStyle.THIN);
        infoStyle.setBorderRight(BorderStyle.THIN);
        infoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Encabezados de tabla
        
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.MEDIUM);
        headerStyle.setBorderTop(BorderStyle.MEDIUM);
        headerStyle.setBorderLeft(BorderStyle.MEDIUM);
        headerStyle.setBorderRight(BorderStyle.MEDIUM);

        // Celdas normales
        
        CellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setFont(normalFont);
        normalStyle.setBorderBottom(BorderStyle.THIN);
        normalStyle.setBorderTop(BorderStyle.THIN);
        normalStyle.setBorderLeft(BorderStyle.THIN);
        normalStyle.setBorderRight(BorderStyle.THIN);

        // Celdas moneda
        
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setFont(normalFont);
        currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("\"S/\" #,##0.00"));
        currencyStyle.setAlignment(HorizontalAlignment.RIGHT);
        currencyStyle.setBorderBottom(BorderStyle.THIN);
        currencyStyle.setBorderTop(BorderStyle.THIN);
        currencyStyle.setBorderLeft(BorderStyle.THIN);
        currencyStyle.setBorderRight(BorderStyle.THIN);

        // Info General
        
        int infoStartRow = 2;

        String[][] info = {
                {"ID Compra:", String.valueOf(compra.getIdCompra())},
                {"Usuario:", compra.getUsuario().getUsername()},
                {"Proveedor:", compra.getProveedor().getNombre()},
                {"Fecha:", compra.getFecha().toString()}
        };

        for (int i = 0; i < info.length; i++) {
            Row row = sheet.createRow(infoStartRow + i);
            row.setHeightInPoints(28);

            Cell label = row.createCell(COL_D);
            label.setCellValue(info[i][0]);
            label.setCellStyle(infoStyle);

            Cell value = row.createCell(COL_E);
            value.setCellValue(info[i][1]);
            value.setCellStyle(infoStyle);
        }

        // Encabezados de la tabla principal
        
        int tableStartRow = 8;
        Row header = sheet.createRow(tableStartRow);
        header.setHeightInPoints(32);

        String[] titles = {"Libro", "Cantidad", "Precio Unitario", "Subtotal"};
        int[] cols = {COL_D, COL_E, COL_F, COL_G};

        for (int i = 0; i < titles.length; i++) {
            Cell cell = header.createCell(cols[i]);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headerStyle);
        }

        // Detalles
        
        int rowNum = tableStartRow + 1;
        for (CompraDetalle d : detalles) {
            Row row = sheet.createRow(rowNum++);
            row.setHeightInPoints(26);

            row.createCell(COL_D).setCellValue(d.getLibro().getTitulo());
            row.getCell(COL_D).setCellStyle(normalStyle);

            row.createCell(COL_E).setCellValue(d.getCantidad());
            row.getCell(COL_E).setCellStyle(normalStyle);

            row.createCell(COL_F).setCellValue(d.getPrecioUniCompra().doubleValue());
            row.getCell(COL_F).setCellStyle(currencyStyle);

            BigDecimal subtotal = d.getPrecioUniCompra().multiply(new BigDecimal(d.getCantidad()));

            row.createCell(COL_G).setCellValue(subtotal.doubleValue());
            row.getCell(COL_G).setCellStyle(currencyStyle);
        }

        // Total de compra
        Row totalRow = sheet.createRow(rowNum);
        totalRow.setHeightInPoints(30);

        Cell totalLabel = totalRow.createCell(COL_F);
        totalLabel.setCellValue("TOTAL:");
        totalLabel.setCellStyle(headerStyle);

        Cell totalValue = totalRow.createCell(COL_G);
        totalValue.setCellValue(compra.getTotalCompra().doubleValue());
        totalValue.setCellStyle(currencyStyle);

        // Enviar respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"Compra_" + idCompra + ".xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        doGet(request, response);
    }
}
