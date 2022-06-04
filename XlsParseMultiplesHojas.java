package ar.com.educacionit.practicasemana13;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ar.com.educacionit.domain.Articulos;
import ar.com.educacionit.generic.BaseFile;

import ar.com.educacionit.generic.IFileParser;
import ar.com.educacionit.generic.ParseException;


public class XlsParseMultiplesHojas extends BaseFile implements IFileParser<Collection<Articulos>> {
	
	public XlsParseMultiplesHojas(String path) {
		super(path);
	}

	//implementar el metodo generico, PERO, dandole un tipo concreto 
	public Collection<Articulos> parse() throws ParseException {
		//libreria poi de apache
				
		//leer un binario
		File xlsxFile= new File(super.getFilepath());
		Collection<Articulos> articulos = new ArrayList<Articulos>();

		
		//clases propias de la libreria
		
		try (InputStream xlsxFileInputStream= new FileInputStream(xlsxFile);
		
				Workbook workbook = new XSSFWorkbook(xlsxFileInputStream)){
			for(int i=0;i < workbook.getNumberOfSheets();i++) {
			
			
			Sheet hojas = workbook.getSheetAt(i);
			//collection
			
			Iterator <Row> filasDelaHoja0 = hojas.iterator();
			
			boolean primerFila=true;
			//TITULO	CODIGO	PRECIO	STOCK	MARCAID	CATEGORIASID
			
			
			while (filasDelaHoja0.hasNext()) {
				Row filaActual = filasDelaHoja0.next();
				
				if (primerFila) {
					primerFila=false;
					continue;
				}
				
				//MONITOR	MONI000	24500	2	1	1
				Iterator<Cell> celdas =filaActual.iterator();
				
				//arriculo
				Articulos articulo = new Articulos();
				
				while (celdas.hasNext()) {
					fromCellToArticulos(celdas, articulo);
				}
				
				articulos.add(articulo);
			}
			
		}
		} catch (IOException fnfe) {
			throw new ParseException("No se ha podido parsear el archivo"+ getFilepath(),fnfe);
		}
	
		
		return articulos;
		
		
		
		
	}

	private void fromCellToArticulos(Iterator<Cell> celdas, Articulos articulo) {
		Cell celdaActual = celdas.next();
		int indiceColumna=celdaActual.getColumnIndex();
		String valor;
		
		switch (celdaActual.getColumnIndex()) {
		case 0:
			valor=celdaActual.getStringCellValue();// valor de la celda monitor
			articulo.setTitulo(valor);
			break;
		case 1:
			valor=celdaActual.getStringCellValue();// valor de la celda monitor
			articulo.setCodigo(valor);
			break;
		case 2:
			Double valorD=celdaActual.getNumericCellValue();// valor de la celda monitor
			articulo.setPrecio(valorD);
			break;
		case 3:
			//byte < short < int < long < float < double
			//String valorAux= celdaActual.getStringCellValue();
			Double stock =(Double)celdaActual.getNumericCellValue();// valor de la celda monitor
			//articulo.setStock(Long.parseLong(valorAux));
			articulo.setStock(stock.longValue());
			break;	
		case 4:
			//byte < short < int < long < float < double
			Double marcasId =(Double)celdaActual.getNumericCellValue();// valor de la celda monitor
			articulo.setMarcasId(marcasId.longValue());
			break;	
		case 5:
			//byte < short < int < long < float < double
			Double categoriasId =(Double)celdaActual.getNumericCellValue();// valor de la celda monitor
			articulo.setCategoriasId(categoriasId.longValue());
			break;
		default:
			break;
		}
		articulo.setFechaCreacion(new Date());
	}
}

