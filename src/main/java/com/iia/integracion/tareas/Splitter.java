package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alvaro
 */
public class Splitter extends Tarea {

    public Splitter(List<Slot> entradas, List<Slot> salidas, String xPathEspression) {
        super(entradas, salidas, xPathEspression);
    }

    @Override
    public void ejecuta() {
        while (entradas.getFirst().numMensajes() > 0) {
            try {
                Mensaje msg = entradas.getFirst().leerSlot();
                UUID idOriginal = msg.getId();
                //System.out.println(msg.toString());
                Document doc = msg.getCuerpo();

                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xpath = xPathFactory.newXPath();
                NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = nodes.item(i);
                    Mensaje fragmento = new Mensaje(crearDocumentoDesdeNodo(node), msg.getId());
                    fragmento.setId(idOriginal);
                    fragmento.setTamano(nodes.getLength());
                    //System.out.println(fragmento.toString());

                    salidas.getFirst().escribirSlot(fragmento);
                }
            } catch (XPathExpressionException ex) {
                Logger.getLogger(Splitter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Splitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Document crearDocumentoDesdeNodo(Node node) throws Exception {
        Document nuevoDoc = javax.xml.parsers.DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Node importado = nuevoDoc.importNode(node, true);
        nuevoDoc.appendChild(importado);
        return nuevoDoc;
    }

    /*private Document crearDocumentoDesdeNodo(Node node) throws Exception {
        Document nuevoDoc = javax.xml.parsers.DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .newDocument();

        Node currentNode = node;

        // Crear una pila para reconstruir la jerarquía desde el nodo hasta la raíz
        java.util.Stack<Node> stack = new java.util.Stack<>();

        while (currentNode != null && currentNode.getNodeType() == Node.ELEMENT_NODE) {
            stack.push(currentNode);
            currentNode = currentNode.getParentNode();
        }

        // Reconstruir el documento desde la raíz hacia el nodo
        Node parentNode = nuevoDoc;
        while (!stack.isEmpty()) {
            Node originalNode = stack.pop();
            Node newNode;
            if (stack.isEmpty()) {
                newNode = nuevoDoc.importNode(originalNode, true);
            } else {
                newNode = nuevoDoc.importNode(originalNode, false);
                if (originalNode.hasAttributes()) {
                    for (int i = 0; i < originalNode.getAttributes().getLength(); i++) {
                        Node attr = originalNode.getAttributes().item(i);
                        Node newAttr = nuevoDoc.importNode(attr, true);
                        newNode.getAttributes().setNamedItem(newAttr);
                    }
                }
            }
            parentNode.appendChild(newNode);
            parentNode = newNode;
        }
        return nuevoDoc;
    }*/
}
