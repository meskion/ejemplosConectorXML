package gestorDOM;
import java.io.File;

public class TestDom {

	public static void main(String[] args) {

		File f = new File("data/libros.xml");
		GestorDOM gestor = new GestorDOM();
		gestor.abrirXmlDom(f);
		gestor.annadirLibro("El Nombre del Viento", "Pat", "2005");
		String datos = gestor.recorrerDOM();
		gestor.writeXML();
		
		System.out.println(datos);
	}

}
