package gestorSAX;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class GestorSAX {

	ManejadorSAX sh;
	SAXParser parser;
	File ficheroXML;

	public int abrirXML(File fichero) {
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();

			parser = factory.newSAXParser();

			sh = new ManejadorSAX();

			ficheroXML = fichero;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public String recorrerSAXyMostrar() {
		try {
			parser.parse(ficheroXML, sh);

			return sh.cadenaResultado;
		} catch (Exception e) {
			e.printStackTrace();
			return "Error al parsear";
		}

	}
}