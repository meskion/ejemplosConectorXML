package gestorSAX;

import java.io.File;

public class TestManejadorSAX {

	public static void main(String[] args) {
		
		File f = new File("data/libros.xml");
		GestorSAX gestor = new GestorSAX();
		gestor.abrirXML(f);
		System.out.println(gestor.recorrerSAXyMostrar());
		

	}

}
