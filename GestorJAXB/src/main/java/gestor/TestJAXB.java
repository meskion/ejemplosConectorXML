package gestor;

import java.io.File;

public class TestJAXB {

	public static void main(String[] args) {
		File f = new File("data/LibrosXML.xml");
		gestorJAXB gestor = new gestorJAXB();
		gestor.abrir_XML_JAXB(f);
		System.out.println(gestor.recorrerJAXByMostrar());
	}
}
