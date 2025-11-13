package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
///////////////////////////////////////////////////////////////////////////////////////////////
///No utiliza xpath expresion
/**
 *
 * @author Alvaro
 */
public class Enricher extends Tarea {

    private String valor;
    private String xpathExpression;

    public Enricher(List<Slot> entradas, List<Slot> salidas, String xpath, String valor) {
        super(entradas, salidas);
        this.valor = valor;
        this.xpathExpression = xpath;
    }

    @Override
    public void ejecuta() {
        Mensaje msg = entradas.getFirst().leerSlot();
        System.out.println(msg);
        Document cuerpoMensaje = msg.getCuerpo();

        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            XPathExpression expr = xpath.compile(xpathExpression);

            Node targetNode = (Node) expr.evaluate(cuerpoMensaje, XPathConstants.NODE);

            if (targetNode != null) {
                targetNode.setTextContent(valor);
            } else {
                crearEstructuraXPath(cuerpoMensaje, xpathExpression, valor);
            }

        } catch (Exception e) {
            System.err.println("Error al enriquecer el mensaje con XPath: " + e.getMessage());
            e.printStackTrace();
        }

        Mensaje msgFinal = new Mensaje(cuerpoMensaje);
        System.out.println(msgFinal);
        salidas.getFirst().escribirSlot(msgFinal);
    }

    /**
     * Método auxiliar para crear la estructura XPath si no existe
     */
    private void crearEstructuraXPath(Document doc, String xpathExpression, String value) {
        try {
            String[] parts = xpathExpression.split("/");
            Node currentNode = doc.getDocumentElement();

            for (String part : parts) {
                if (part.isEmpty() || part.equals(doc.getDocumentElement().getNodeName())) {
                    continue; 
                }

                NodeList children = currentNode.getChildNodes();
                Node foundNode = null;

                for (int i = 0; i < children.getLength(); i++) {
                    if (children.item(i).getNodeName().equals(part)) {
                        foundNode = children.item(i);
                        break;
                    }
                }

                if (foundNode == null) {
                    Element newElement = doc.createElement(part);
                    currentNode.appendChild(newElement);
                    currentNode = newElement;
                } else {
                    currentNode = foundNode;
                }
            }
            currentNode.setTextContent(value);

        } catch (Exception e) {
            System.err.println("Error al crear estructura XPath: " + e.getMessage());
        }
    }
}
