package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

///////////////////////////////////////////////////////////////////////////////
///comprobar como salen
/**
 *
 * @author Alvaro
 */
public class Correlator extends Tarea {

    private Map<UUID, Mensaje> mensajesEntrantesCorelator;
    private Map<String, Mensaje> mensajesEntrantesSinCorrelator;
    private String xpathExpression;
    public Correlator(List<Slot> entradas, List<Slot> salidas, String xpath) {
        super(entradas, salidas);
        this.xpathExpression = xpath;
        mensajesEntrantesCorelator = new HashMap<>();
        mensajesEntrantesSinCorrelator = new HashMap<>();
    }

    @Override
    public void ejecuta() {
        while (entradas.getFirst().numMensajes() > 0 || entradas.get(1).numMensajes() > 0) {
            relaciona(entradas.getFirst().leerSlot());
            relaciona(entradas.get(1).leerSlot());
        }
    }

    public void relaciona(Mensaje msg) {
        if (msg != null) {
            if (msg.getIdCorrelator() != null) {
                relacionaPorId(msg);
                System.out.println("Relacionando por id");
            } else if (msg.getIdCorrelator() == null) {
                relacionaSinId(msg);
                System.out.println("Relaciona sin id");
            }
        }
    }

    public void relacionaPorId(Mensaje msg) {
        if (mensajesEntrantesCorelator.containsKey(msg.getIdCorrelator())) {
            salidas.getFirst().escribirSlot(msg);
            salidas.get(1).escribirSlot(mensajesEntrantesCorelator.get(msg.getIdCorrelator()));
            mensajesEntrantesCorelator.remove(msg.getIdCorrelator());
        } else {
            mensajesEntrantesCorelator.put(msg.getIdCorrelator(), msg);
        }
    }

    private void relacionaSinId(Mensaje msg) {
        try {
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            Node node = (Node) xpath.evaluate(xpathExpression, msg.getCuerpo(), XPathConstants.NODE);
            
            if (node == null) {
                //System.out.println("No se encontró nodo con XPath: " + xpathExpression);
                return;
            }
                        String valor = node.getTextContent();
            if (valor == null || valor.trim().isEmpty()) {
                //System.out.println("Nodo vacío con XPath: " + xpathExpression);
                return;
            }
            
            //System.out.println("Valor para correlación: " + valor);
            
            if (mensajesEntrantesSinCorrelator.containsKey(valor)) {
                Mensaje mensajePareja = mensajesEntrantesSinCorrelator.get(valor);
                salidas.get(0).escribirSlot(msg);
                salidas.get(1).escribirSlot(mensajePareja);
                mensajesEntrantesSinCorrelator.remove(valor);
                //System.out.println("Pareja encontrada por valor: " + valor);
            } else {
                mensajesEntrantesSinCorrelator.put(valor, msg);
                //System.out.println("Mensaje guardado esperando pareja con valor: " + valor);
            }
        } catch (XPathExpressionException x) {
            System.err.println("Error en expresión XPath: " + xpathExpression);
            x.printStackTrace();
        }
    }
}
