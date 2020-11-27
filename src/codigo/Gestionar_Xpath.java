/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/**
 *
 * @author aleja
 */
public class Gestionar_Xpath {

    //Objeto Document que almacena el DOM del XML seleccionado.
    Document doc;

    XPath xpath;

    public int abrir_XML(File _fichero) {

        //doc representará el árbol DOM.
        doc = null;

        try {
            //Crea un fichero DocumentBuilderFactory para el DOM (XAXP)
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            //Atributos del documento.
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            //Se crea un objeto DocumentBuilder para cargar en él
            //la estructura de árbol DOM de un fichero seleccionado
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Interpreta este fichero XML, lo mapea y guarda en memoria y
            //da el apuntador a la raíz.
            doc = builder.parse(_fichero);
            //Ahorastá listo para ser recorrido.

            xpath = XPathFactory.newInstance().newXPath();

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String Ejecutar_XPath(String _consulta) {
        String salida = "";
        Node node;
        String datos_nodo[] = null;

        try {

            XPathExpression exp = xpath.compile(_consulta);

            Object resultado = exp.evaluate(doc, XPathConstants.NODESET);
            NodeList listaNodos = (NodeList) resultado;

            Node nodoRecibido = (Node) exp.evaluate(doc, XPathConstants.NODE);
            //System.out.println("Tipo de nodo: " + nodoRecibido.getNodeName());

            if (nodoRecibido.getNodeName() == "Libro") {
                for (int i = 0; i < listaNodos.getLength(); i++) {
                    node = listaNodos.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        datos_nodo = procesarLibro(node);
                        salida += "\r\n " + "Publicado en: " + datos_nodo[0];
                        salida += "\r\n " + "El título es: " + datos_nodo[1];
                        salida += "\r\n " + "El autor es: " + datos_nodo[2];
                        salida += "\r\n " + "La editorial es: " + datos_nodo[3];
                        salida += "\r\n -------------------------";
                    }
                }
            } else if (nodoRecibido.getNodeName() == "Autor" || nodoRecibido.getNodeName() == "Titulo" || nodoRecibido.getNodeName() == "Editorial") {
                for (int j = 0; j < listaNodos.getLength(); j++) {
                    salida += "\n" + listaNodos.item(j).getFirstChild().getNodeValue();
                }
            } else {
                for (int k = 0; k < listaNodos.getLength(); k++) {
                    salida += "\n" + listaNodos.item(k).getNodeValue();
                }
            }
            return salida;
        } catch (Exception e) {
            return "Error en la ejecución de la salida";
        }
    }

    private String[] procesarLibro(Node _n) {
        String datos[] = new String[4];
        Node hijosDeLibro = null;
        int contador = 1;

        //Obtiene el valor del primer atributo del nodo
        datos[0] = _n.getAttributes().item(0).getNodeValue();

        NodeList nodos = _n.getChildNodes();

        for (int i = 0; i < nodos.getLength(); i++) {
            hijosDeLibro = nodos.item(i);

            if (hijosDeLibro.getNodeType() == Node.ELEMENT_NODE) {
                //Para acceder al texto con el título y autor 
                //accedo al nodo text hijo de ntemp y se saca su valor.
                datos[contador] = hijosDeLibro.getFirstChild().getNodeValue();
                contador++;
            }
        }

        return datos;
    }
}
