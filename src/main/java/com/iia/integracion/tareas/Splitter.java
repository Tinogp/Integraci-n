package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
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
 * @author tinog
 */
public class Splitter extends Tarea {

    public Splitter(List<Slot> entradas, List<Slot> salidas, String xPathEspression) {
        super(entradas, salidas, xPathEspression);
    }

    @Override
    public void ejecuta() {
        try {
            Mensaje msg = entradas.getFirst().leerSlot();
            Document doc = msg.getCuerpo();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            NodeList nodes = (NodeList) xpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                Mensaje fragmento = new Mensaje(crearDocumentoDesdeNodo(node));
                System.out.println(fragmento.toString());
                salidas.getFirst().escribirSlot(fragmento);
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(Splitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Splitter.class.getName()).log(Level.SEVERE, null, ex);
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
}