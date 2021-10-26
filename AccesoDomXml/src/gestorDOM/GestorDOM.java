package gestorDOM;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GestorDOM {

	private Document doc;

	public int abrirXmlDom(File fichero) {
		doc = null;

		try {
			// nueva instancia de la factoria
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// para que ignore comentarios en xml
			factory.setIgnoringComments(true);
			// para que ignore espacios en blanco redundantes en el xml
			factory.setIgnoringElementContentWhitespace(true);
			// para que no valide el documento xml segun un schema
			factory.setValidating(false);
			// Creamos el builder con la factoria recien creada
			DocumentBuilder builder = factory.newDocumentBuilder();
			// Construimos el documento pasado por parametro
			doc = builder.parse(fichero);
			// si llega hasta aqui el proceso, ha funcionado, así que devolvemos 0
			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			// Si captura una excepción, ha fallado al abrir documento, devuelve -1
			return -1;
		}
	}

	public String recorrerDOM() {

		String[] datosNodos = null;
		String salida = "";
		Node node;
		// nodo raiz del documento
		Node raiz = doc.getFirstChild();
		// Hijos del nodo raiz
		NodeList nodosHijos = raiz.getChildNodes();
		// recorremos los nodos hijos
		for (int i = 0; i < nodosHijos.getLength(); i++) {
			node = nodosHijos.item(i);
			// comprobamos que el nodo es un elemento y no texto
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				// procesamos el nodo libro en un string
				datosNodos = procesarLibros(node);
				// construimos un string con todos los libros del xml
				salida = salida + "\n publicado en " + datosNodos[0] + "\n Titulo: " + datosNodos[1] + "\n Autor: "
						+ datosNodos[2];
			}
		}
		return salida;
	}

	private String[] procesarLibros(Node node) {

		String[] datos = new String[3];
		Node ntm = null;
		// este contador es porque nos vamos a saltar la posicion con el atributo
		// "publicado_en"
		int contador = 1;
		// Cogemos el primer aributo(el unico) y lo guardamos en la posicion 0
		datos[0] = node.getAttributes().item(0).getNodeValue();
		// guardamos los hijos del nodo libro
		NodeList nodos = node.getChildNodes();
		// recorremos los hijos de "libro", que son "titulo" y "autor"
		for (int i = 0; i < nodos.getLength(); i++) {
			ntm = nodos.item(i);
			// comprobamos que no son texto
			if (ntm.getNodeType() == Node.ELEMENT_NODE) {
				// guardamos el valor del texto( que es un nodo) de cada hijo de libro
				datos[contador] = ntm.getChildNodes().item(0).getNodeValue();

				/*
				 * tambien se puede usar esta linea, pero sabiendo que coge el texto de los
				 * hijos del nodo ntm. datos[contador] = ntm.getTextContent();
				 */
				contador++;
			}
		}
		return datos;
	}

	private int annadirLibro(Document doc, String titulo, String autor, String anyo) {
		try {

//			Node libro = doc.createElement("Libro");
			Element libro = doc.createElement("Libro");

//			Node raiz = doc.getFirstChild();
			Node raiz = doc.getChildNodes().item(0);

			// añade otro hijo a raiz al final de la cola de hijos.
			raiz.appendChild(libro);

			// Creamos un nodo elemento " <Titulo> "
			Node titu = doc.createElement("Titulo");

			// creamos un nodo texto "el titulo que sea"
			Node textTitu = doc.createTextNode(titulo);
			// añadimos el nodo elemento <Titulo> a <Libro>
			libro.appendChild(titu);
			// añadimos el nodo texto "el titulo que sea a <Titulo>
			titu.appendChild(textTitu);
			// y repetimos para el otro hijo ...
			Node aut = doc.createElement("Autor");

			Node textAut = doc.createTextNode(autor);
			libro.appendChild(aut);
			aut.appendChild(textAut);
			
			// añadimos y damos su valor a un atributo del nodo elemento libro
//			 ((Element) libro).setAttribute("publicado_en", anyo);
			libro.setAttribute("publicado_en", anyo);
			
			return 0;

		} catch (Exception e) {
			
			e.printStackTrace();
			return -1;
		}
	}

	public int annadirLibro(String titulo, String autor, String anyo) {
		return annadirLibro(doc, titulo, autor, anyo);
	}

	public void writeXML() {
		try {

			// crea una factoria de transformer
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			// usamos la factoria para cconstruir un transformer
			Transformer transformer = transformerFactory.newTransformer();
			// DOMSource es una clase que guarda el arbol DOM que vamos a escribir en el XML
			DOMSource domSource = new DOMSource(doc);
			// StreamResult guarda la ruta del archivo donde va a escribir el XML
			StreamResult streamResult = new StreamResult(new File("data/librosNew.xml"));

			// Con estos metodos podemos cambiar algunas propiedades del xml como la
			// indentacion para que se vea mejor
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			// Realizamos la transformación y se genera el xml
			transformer.transform(domSource, streamResult);
		} catch (TransformerException e) {
			
			e.printStackTrace();
		}
	}

}
