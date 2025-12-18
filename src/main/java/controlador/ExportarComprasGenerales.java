package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Compras;
import modeloDAO.ComprasDAO;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@WebServlet("/ExportarComprasGenerales")
public class ExportarComprasGenerales extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ComprasDAO comprasDAO = new ComprasDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Compras> listaCompras = comprasDAO.listar();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Compras_General");

        // Columnas
        int COL_D = 3;
        int COL_E = 4;
        int COL_F = 5;
        int COL_G = 6;
        int COL_H = 7;

        // Ancho de columnas
        
        sheet.setColumnWidth(COL_D, 20 * 256); // ID
        sheet.setColumnWidth(COL_E, 20 * 256); // Usuario
        sheet.setColumnWidth(COL_F, 20 * 256); // Proveedor
        sheet.setColumnWidth(COL_G, 18 * 256); // Fecha
        sheet.setColumnWidth(COL_H, 18 * 256); // Total
        sheet.setHorizontallyCenter(true);

        // Fuentes
        
        Font normalFont = workbook.createFont();
        normalFont.setFontHeightInPoints((short) 14);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 15);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        // Estilos
        
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

        CellStyle normalStyle = workbook.createCellStyle();
        normalStyle.setFont(normalFont);
        normalStyle.setBorderBottom(BorderStyle.THIN);
        normalStyle.setBorderTop(BorderStyle.THIN);
        normalStyle.setBorderLeft(BorderStyle.THIN);
        normalStyle.setBorderRight(BorderStyle.THIN);
        normalStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.setFont(normalFont);
        currencyStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        currencyStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        currencyStyle.setDataFormat(workbook.createDataFormat().getFormat("\"S/\" #,##0.00"));
        currencyStyle.setAlignment(HorizontalAlignment.RIGHT);
        currencyStyle.setBorderBottom(BorderStyle.THIN);
        currencyStyle.setBorderTop(BorderStyle.THIN);
        currencyStyle.setBorderLeft(BorderStyle.THIN);
        currencyStyle.setBorderRight(BorderStyle.THIN);

        CellStyle infoStyle = workbook.createCellStyle();
        infoStyle.setFont(normalFont);
        infoStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        infoStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        infoStyle.setBorderTop(BorderStyle.THIN);
        infoStyle.setBorderBottom(BorderStyle.THIN);
        infoStyle.setBorderLeft(BorderStyle.THIN);
        infoStyle.setBorderRight(BorderStyle.THIN);
        infoStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Encabezado tabla
        
        int tableStartRow = 2;
        Row header = sheet.createRow(tableStartRow);
        header.setHeightInPoints(32);

        String[] titles = {"ID Compra", "Usuario", "Proveedor", "Fecha", "Total"};
        int[] cols = {COL_D, COL_E, COL_F, COL_G, COL_H};

        for (int i = 0; i < titles.length; i++) {
            Cell cell = header.createCell(cols[i]);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headerStyle);
        }

        // Filas de datos
        
        int rowNum = tableStartRow + 1;
        for (Compras c : listaCompras) {
            Row row = sheet.createRow(rowNum++);
            row.setHeightInPoints(26);

            Cell c1 = row.createCell(COL_D);
            c1.setCellValue(c.getIdCompra());
            c1.setCellStyle(infoStyle); 

            Cell c2 = row.createCell(COL_E);
            c2.setCellValue(c.getUsuario().getUsername());
            c2.setCellStyle(infoStyle); 

            Cell c3 = row.createCell(COL_F);
            c3.setCellValue(c.getProveedor().getNombre());
            c3.setCellStyle(infoStyle);

            Cell c4 = row.createCell(COL_G);
            c4.setCellValue(c.getFecha().toString());
            c4.setCellStyle(infoStyle);

            Cell c5 = row.createCell(COL_H);
            c5.setCellValue(c.getTotalCompra().doubleValue());
            c5.setCellStyle(currencyStyle); 
        }

        // Respuesta HTTP
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"Compras_General.xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
