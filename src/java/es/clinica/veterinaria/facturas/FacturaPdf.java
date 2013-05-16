package es.clinica.veterinaria.facturas;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import es.clinica.veterinaria.ventas.Venta;
import es.clinica.veterinaria.ventas_linea.VentaLinea;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;

/**
 * @author SaRCo
 */

public class FacturaPdf {
    private Document document;
    private Factura factura;
//    private String FILE = "c:/temp/factura-";
//    private Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
//    private Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font small = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    private BaseFont bfBold, bf;
    
    public FacturaPdf() {}

    public void createPdf(){
        try {
                PdfWriter docWriter = null;
                initializeFonts();
                FacturaDAO facturaDao = new FacturaDAO();
                document = new Document(PageSize.A4);
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(factura.getFecha());
                String sfactura = getDirectorio().toString() + "/factura-" + fecha + "-" + factura.getNumero() + ".pdf";

                System.out.println("Directorio factura: " + sfactura);
                docWriter = PdfWriter.getInstance(document, new FileOutputStream(sfactura));
                document.open();

                PdfContentByte cb = docWriter.getDirectContent();

                addMetaData(document);
                addLogo(document);
                generateHeader(document, cb);
                addTitlePage(document);
                document.add(createTable());
                document.close();
                factura.setRuta("../facturaspdf/factura-" + fecha + "-" + factura.getNumero() + ".pdf");
                facturaDao.updatePDF(factura);
                System.out.println("Documento cerrado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    //add the images
    private void addLogo(Document doc) throws DocumentException, Exception{
        try {

            String logo = Executions.getCurrent().getDesktop().getWebApp().getRealPath("logo.png");
            Image companyLogo = Image.getInstance(logo);
            companyLogo.setAbsolutePosition(35,700);
            companyLogo.scalePercent(20);
            doc.add(companyLogo);
        }
        catch (DocumentException dex){
            dex.printStackTrace();
        }
        catch (Exception ex){
           ex.printStackTrace();
        }
    }
  // iText allows to add metadata to the PDF which can be viewed in your Adobe
  // Reader
  // under File -> Properties
    private void addMetaData(Document document) {
        document.addTitle("Factura Nº: " + factura.getNumero());
        document.addSubject("Fecha:" + new SimpleDateFormat("yyyy").format(factura.getFecha()));
        document.addKeywords("Facturas PDF");
        document.addAuthor("agustin.alvarez.garcia@gmail.com");
        document.addCreator("agustin.alvarez.garcia@gmail.com");
    }

    private  void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        String fecha = new SimpleDateFormat("dd / MM / yyyy").format(factura.getFecha());

//        Paragraph titulo = new Paragraph("FACTURA Nº " + factura.getNumero() +
//                                  "\nFecha: " + fecha, catFont);
//        titulo.setAlignment(Element.ALIGN_RIGHT);

//        preface.add(titulo);
        addEmptyLine(preface, 2);

        document.add(preface);

//        preface = new Paragraph("CENTRO VETERINARIO \"El Rey de la Casa\"");
        preface.setAlignment(Element.ALIGN_RIGHT);
        addEmptyLine(preface, 2);
        document.add(preface);

//        preface = new Paragraph("Cliente: " + factura.getCliente().getFullname() +
//                                "\nNIF/CIF: " + factura.getCliente().getNif() +
//                                "\n" + factura.getCliente().getDireccion() +
//                                "\n" + factura.getCliente().getCiudad().getPoblacion() +
//                                " (" + factura.getCliente().getProvincia().getProvincia() + ")" +
//                                "\nTeléfono: " + factura.getCliente().getTelefono(), smallBold);
//        preface.setAlignment(Element.ALIGN_LEFT);
        addEmptyLine(preface, 2);
        document.add(preface);
        //preface.add(new Paragraph("This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
        //    redFont));

        //document.add(preface);
        // Start a new page
        // document.newPage();
    }


    private  void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
                paragraph.add(new Paragraph(" "));
        }
    }

    private File getDirectorio(){
        Desktop desktop = Executions.getCurrent().getDesktop();
        String realpath = desktop.getWebApp().getRealPath("/facturaspdf");

        File baseDir = new File(realpath + "/");

        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }

        return baseDir;
    }

    public PdfPTable createTable() throws DocumentException {
    	// a table with three columns
        int iva = 0, iva2= 0;
        DecimalFormat df = new DecimalFormat("0.00");
        PdfPTable table = new PdfPTable(6);
        table.setTotalWidth(new float[]{ 50, 65, 150, 150, 65, 70});
        table.setLockedWidth(true);

        // the cell object
        // we add a cell with colspan 3
        PdfPCell cell = new PdfPCell(new Phrase("CANT."));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("FECHA"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("CONCEPTO"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("DESCRIPCIÓN"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("PRECIO"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("IMPORTE"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        HashSet<Venta> ventas = this.getFactura().getVentas();
        for(Venta venta : ventas)
        {
            HashSet<VentaLinea> listVenta = venta.getVenta_lineas();

            for(VentaLinea vlinea : listVenta)
            {
                if(vlinea.getTipo() == 1){
                    if(vlinea.getProducto().getIva() != null  ) {
                        iva2 = vlinea.getProducto().getIva().getValor();
    //                    System.out.println("IVA: " +iva2);
                    }
                }
                else if(vlinea.getTipo() == 2){
                    if(vlinea.getServicio().getIva() != null) {
                        iva2 = vlinea.getServicio().getIva().getValor();
    //                    System.out.println("IVA: " +iva2);
                    }
                }

                //Para hacer el calculo nos vamos a quedar con el IVA mayor
                if(iva < iva2){
                    iva = iva2;
                }

                cell = new PdfPCell(new Phrase(vlinea.getCantidad() + "", small));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingBottom(5);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(new SimpleDateFormat("dd-MM-yyyy").format(vlinea.getFecha()), small));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPaddingBottom(5);
                table.addCell(cell);
                
                if(vlinea.getTipo() == 2){ //Servicio
                    table.addCell(new PdfPCell(new Phrase(vlinea.getNombre(), small)));
                    
                    String descripcion = vlinea.getDescripcion();
                    if(descripcion == null || "null".equals(descripcion)) {
                        table.addCell(new PdfPCell(new Phrase(" ",small)));
        //                System.out.println("null:" + descripcion);
                    }
                    else {
                        table.addCell(new PdfPCell(new Phrase(descripcion,small)));
        //                System.out.println("!null:" + descripcion);
                    }
                }
                else if(vlinea.getTipo() == 1){ //Producto
                //Si el producto es Tratamiento
                    if(vlinea.getProducto().getFamilia().isTratamiento()) {
                        table.addCell(new PdfPCell(new Phrase("Tratamiento", small)));
                        table.addCell(new PdfPCell(new Phrase("" ,small)));
                    }
                    else{

                        table.addCell(new PdfPCell(new Phrase(vlinea.getNombre(), small)));

                        String descripcion = vlinea.getDescripcion();
                        if(descripcion == null || "null".equals(descripcion)) {
                            table.addCell(new PdfPCell(new Phrase(" ",small)));
            //                System.out.println("null:" + descripcion);
                        }
                        else {
                            table.addCell(new PdfPCell(new Phrase(descripcion,small)));
            //                System.out.println("!null:" + descripcion);
                        }
                    }
                }
                cell = new PdfPCell(new Phrase(df.format(vlinea.getPvp()) + " €", small));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingBottom(5);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(df.format(vlinea.getPreciototalNoIVA()) + " €", small));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingBottom(5);
                table.addCell(cell);
            }
        }

        cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(1);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthLeft(0);
        cell.setBorder(0);
        cell.setBorderColorLeft(BaseColor.WHITE);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("SUMA"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);


        cell = new PdfPCell(new Phrase(df.format(factura.getCoste()) +" €", smallBold));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        /* IVA */
        cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(1);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthLeft(0);
        cell.setBorder(0);
        cell.setBorderColorLeft(BaseColor.WHITE);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);

        float costetotal = (float) (factura.getCostetotal());

        cell = new PdfPCell(new Phrase("IVA " + iva + "%"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(df.format(factura.getIvas()) +" €", smallBold));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        /* COSTE TOTAL */
        cell = new PdfPCell(new Phrase(" "));
        cell.setColspan(1);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthLeft(0);
        cell.setBorder(0);
        cell.setBorderColorLeft(BaseColor.WHITE);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setPaddingBottom(5);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("TOTAL"));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(df.format(costetotal) +" €", smallBold));
        cell.setColspan(1);
        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
        cell.setPaddingBottom(5);
        table.addCell(cell);

        return table;
    }

    

    private void createHeadings(PdfContentByte cb, float x, float y, String text, int tam){
        cb.beginText();
        cb.setFontAndSize(bfBold, tam);
        cb.setFontAndSize(bfBold, tam);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();
    }
    
    private void createCliente(PdfContentByte cb, float x, float y, String text, int tam){
        cb.beginText();
        cb.setFontAndSize(bf, tam);
        cb.setFontAndSize(bf, tam);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();
    }
   
    private void initializeFonts() throws DocumentException , IOException {
        try {
            bfBold = BaseFont.createFont(BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void generateHeader(Document doc, PdfContentByte cb) {
        try {

            createHeadings(cb, 150, 790, "Centro Veterinario \"El Rey de la Casa\"", 10);
            createHeadings(cb, 150, 775, "Luis Miguel Puerto del Canto", 8);
            createHeadings(cb, 150, 760, "NIF: 25.594.942-J", 8);
            createHeadings(cb, 150, 745, "C/ Comandante Vázquez Ramos, 4", 8);
            createHeadings(cb, 150, 730, "Montellano (Sevilla)", 8);
            createHeadings(cb, 150, 715, "Teléfono: 626 31 06 64", 8);

            cb.setLineWidth(1f);
 
            // Invoice Header box layout
//            cb.rectangle(70,600,150,60);
            cb.roundRectangle(460, 770, 100, 50, 5 ); //x, y, ancho, alto, round
            cb.roundRectangle(460, 705, 100, 50, 5 ); //x, y, ancho, alto, round
            cb.moveTo(460,797); // (x,y)
            cb.lineTo(560,797);
            cb.moveTo(460,732);
            cb.lineTo(560,732);
            cb.stroke();
   
            createHeadings(cb, 480, 804, "FACTURA", 12);
            createHeadings(cb, 502, 780, "" + factura.getNumero(), 14);
            createHeadings(cb, 488, 739, "FECHA", 12);
            createHeadings(cb, 470, 715, new SimpleDateFormat("dd / MM / yyyy").format(factura.getFecha()), 14);
            
            cb.setLineWidth(1f);
            cb.roundRectangle(25, 565, 540, 120, 5 ); //x, y, ancho, alto, round
            cb.stroke();
            
//            cb.setGrayStroke(1f);
//            cb.rectangle(25, 665, 299, 20); //x,y, ancho, alto
            
//            cb.setGrayStroke(20f);
            cb.moveTo(25,660); // (x,y)
            cb.lineTo(565,660);
            cb.stroke();
            
            createHeadings(cb, 265, 667, "CLIENTE" , 12);
            createCliente(cb, 35, 643, "Nombre:    " + factura.getCliente().getFullname(), 11);
            createCliente(cb, 35, 626, "NIF/CIF:    " + factura.getCliente().getNif(), 11);
            createCliente(cb, 35, 608, "Dirección:  " + factura.getCliente().getDireccion(), 11);
            createCliente(cb, 35, 590, "Ciudad:     " + factura.getCliente().getCiudad().getPoblacion() + ", " + factura.getCliente().getCodigopostal() + ",  (" + factura.getCliente().getProvincia().getProvincia() + ")" , 11);
            createCliente(cb, 35, 572, "Teléfono:   " + factura.getCliente().getTelefono(), 11);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}