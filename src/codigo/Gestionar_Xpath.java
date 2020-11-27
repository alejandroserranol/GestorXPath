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
 * @author Alejandro Serrano Loredo
 * @version 1
 */
public class Gestionar_Xpath {

    //Objeto Document que almacena el DOM del XML seleccionado.
    Document doc;

    XPath xpath;

    /**
     * @see Crea al árbol DOM y prepara el fichero pasado como parámetro
     *      para ejecutar el XPath.
     * @param _fichero: Fichero a partir del cual se elaborará el DOM.
     * @return Entero para manejar la excepción.
     */
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
    
    /**
     * @see Realiza la consulta con el XPath y devuelve el resultado en forma de String.
     * @param _consulta: Expresión a ejecutar el contenido de jTextAreaConsulta.
     * @return String a mostrar en el jTextAreaSalida.
     */
    public String Ejecutar_XPath(String _consulta) {
        String salida = "";
        NodeList listaLibros = null;
        Node nodoLibro;
        String datos_nodo[] = null;
        int posicion = 1;

        try {

            XPathExpression exp = xpath.compile(_consulta);

            Object resultado = exp.evaluate(doc, XPathConstants.NODESET);
            NodeList listaNodos = (NodeList) resultado;
            //Guarda la consulta en un nodo para comprobar de qué tipo es
            Node nodoRecibido = (Node) exp.evaluate(doc, XPathConstants.NODE);

            if (nodoRecibido.getNodeName().equals("Libros")) {

                listaLibros = nodoRecibido.getChildNodes();

                salida += "Se van a mostrar los siguientes libros\n************************************************\n";
                for (int i = 0; i < listaLibros.getLength(); i++) {
                    nodoLibro = listaLibros.item(i);
                    if (nodoLibro.getNodeType() == Node.ELEMENT_NODE) {
                        datos_nodo = procesarLibro(nodoLibro);
                        salida += "Libro " + posicion + "\r\n-------------------------";
                        salida += "\r\nEl título es:\t" + datos_nodo[1];
                        salida += "\r\nEl autor es:\t" + datos_nodo[2];
                        salida += "\r\nLa editorial es:\t" + datos_nodo[3];
                        salida += "\r\nPublicado en:\t" + datos_nodo[0];
                        salida += "\r\n-------------------------\r\n";
                        posicion++;
                    }
                }
            } else if (nodoRecibido.getNodeName().equals("Libro")) {
                salida += "Se van a mostrar los  siguientes libros\n************************************************\n";
                for (int j = 0; j < listaNodos.getLength(); j++) {
                    nodoLibro = listaNodos.item(j);
                    if (nodoLibro.getNodeType() == Node.ELEMENT_NODE) {
                        datos_nodo = procesarLibro(nodoLibro);
                        salida += "Libro " + posicion + "\r\n-------------------------";
                        salida += "\r\nEl título es:\t" + datos_nodo[1];
                        salida += "\r\nEl autor es:\t" + datos_nodo[2];
                        salida += "\r\nLa editorial es:\t" + datos_nodo[3];
                        salida += "\r\nPublicado en:\t" + datos_nodo[0];
                        salida += "\r\n-------------------------\r\n";
                        posicion++;
                    }
                }
            } else if (nodoRecibido.getNodeName().equals("Autor")) {
                for (int k = 0; k < listaNodos.getLength(); k++) {
                    salida += "\n\nAutor " + posicion + ":\t" + listaNodos.item(k).getFirstChild().getNodeValue();
                    posicion++;
                }
            } else if (nodoRecibido.getNodeName().equals("Titulo")) {
                for (int m = 0; m < listaNodos.getLength(); m++) {
                    salida += "\n\nTitulo " + posicion + ":\t" + listaNodos.item(m).getFirstChild().getNodeValue();
                    posicion++;
                }
            } else if (nodoRecibido.getNodeName().equals("Editorial")) {
                for (int n = 0; n < listaNodos.getLength(); n++) {
                    salida += "\n\nEditorial " + posicion + ":\t" + listaNodos.item(n).getFirstChild().getNodeValue();
                    posicion++;
                }
            } else {
                for (int p = 0; p < listaNodos.getLength(); p++) {
                    salida += "\r\nLibro " + posicion + "\r\n -------------------------\r\n";
                    salida += "Publicado en:\t" + listaNodos.item(p).getNodeValue();
                    salida += "\r\n-------------------------";
                    posicion++;
                }
            }
            return salida;
        } catch (Exception e) {
            return "Error en la ejecución de la salida";
        }
    }
    
    /**
     * @see Recorre un libro pasado como parámetro y devuelve su información.
     * @param _nodoLibro: Nodo a recorrer, del que mostrar información.
     * @return Array de Strings con información relativa al libro.
     */
    private String[] procesarLibro(Node _nodoLibro) {
        String datos[] = new String[4];
        Node hijosDeLibro = null;
        int contador = 1;

        //Obtiene el valor del primer atributo del nodo
        datos[0] = _nodoLibro.getAttributes().item(0).getNodeValue();

        NodeList nodos = _nodoLibro.getChildNodes();

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
