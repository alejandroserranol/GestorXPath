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
        
        try {
            
            XPathExpression exp = xpath.compile(_consulta);
            
            Node nodoRecibido = (Node) exp.evaluate(doc, XPathConstants.NODE);
            //System.out.println("Tipo de nodo: " + nodoRecibido.getNodeName());
            
            if(nodoRecibido.getNodeName()=="Libro"){
                System.out.println("Es un nodo Libro");
            }
            
            
            Object resultado = exp.evaluate(doc, XPathConstants.NODESET);
            NodeList listaNodos = (NodeList) resultado;
            
            for(int i=0; i<listaNodos.getLength(); i++){
                salida = salida + "\n" + listaNodos.item(i).getFirstChild().getNodeValue();
            }
            
            return salida;
        }catch (Exception e){
            return "Error en la ejecución de la salida";
        }
    }
}
