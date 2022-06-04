package ar.com.educacionit.practicasemana13;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import ar.com.educacionit.domain.Articulos;

public class CrearPDF {
	
	private String path ;

	public CrearPDF(String path) {
		super();
		this.path = path;
	}
	
public void ConstruirPDF (Collection<Articulos> articulos) {
	
	Document pdf = new Document();
	
	try {
		PdfWriter.getInstance(pdf, new FileOutputStream(path+"/reporteArticulos13.pdf"));
		
		pdf.open();
		
		PdfPTable table = new PdfPTable(6);
		table.addCell("titulo");
		table.addCell("codigo");
		table.addCell("precio");
		table.addCell("stock");
		table.addCell("marcasId");
		table.addCell("categoriasId");
		
		// cargar lista articulos
		for (Articulos articulo : articulos) {
			table.addCell(articulo.getTitulo());
			table.addCell(articulo.getCodigo());
			table.addCell(articulo.getPrecio().toString());
			table.addCell(articulo.getStock().toString());
			table.addCell(articulo.getMarcasId().toString());
			table.addCell(articulo.getCategoriasId().toString());
		}
		
		pdf.add(table);
		
		pdf.close();
		
		
		
	} catch (FileNotFoundException | DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
