package ar.com.educacionit.practicasemana13;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.educacionit.domain.Articulos;
import ar.com.educacionit.generic.IFileParser;
import ar.com.educacionit.generic.ParseException;

public class MainParseXlsMultiplesHojasPDF {

	public static void main(String[] args) {
		
		String pathxlsx ="./src/main/java/ar/com/educacionit/practicasemana13/articulosEjercicio13.xlsx";
			
		IFileParser<Collection<Articulos>> parserMult= new XlsParseMultiplesHojas(pathxlsx);
		
		Collection<Articulos> articulos =new ArrayList<Articulos>();
		
		try {
			articulos = parserMult.parse();
			
			for (Articulos arti: articulos) {
				System.out.println(arti);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String pathDestinoPDF="./src/main/java/ar/com/educacionit/practicasemana13";
		
		CrearPDF cp= new CrearPDF(pathDestinoPDF);
		cp.ConstruirPDF(articulos);
		
	}

}
