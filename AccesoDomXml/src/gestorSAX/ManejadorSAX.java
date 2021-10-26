package gestorSAX;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ManejadorSAX extends DefaultHandler {

	public int ultimoElemento;
	public String cadenaResultado = "";

	public ManejadorSAX() {
		ultimoElemento = 0;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("Libro")) {
			cadenaResultado += "Publicado en: " + attributes.getValue(0) + "\n";
			ultimoElemento = 1; // libro
		} else if (qName.equals("Titulo")) {
			cadenaResultado = cadenaResultado + "EL titulo es ";
			ultimoElemento = 2; // Titulo
		} else if (qName.equals("Autor")) {
			cadenaResultado = cadenaResultado + "EL autor es ";
			ultimoElemento = 3; // Autor

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("Libro")) {
			cadenaResultado += "He encontrado el final del elemento \n" + "------------------------------------ \n";
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (ultimoElemento == 2) {
			for (int i = start; i < length + start; i++) {
				cadenaResultado += ch[i];
			}

		} else if (ultimoElemento == 3) {
			for (int i = start; i < length + start; i++) {
				cadenaResultado += ch[i];
			}
		}
	}

}
