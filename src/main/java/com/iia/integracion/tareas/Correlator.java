package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;

/**
 *
 * @author Alvaro
 */
public class Correlator extends Tarea {

    private Map<UUID, List<Mensaje>> mensajesEntrantesCorelator;
    private Map<String, List<Mensaje>> mensajesEntrantesSinCorrelator;
    private String xpathExpression;

    public Correlator(List<Slot> entradas, List<Slot> salidas, String xpath) {
        super(entradas, salidas);
        this.xpathExpression = xpath;
        mensajesEntrantesCorelator = new HashMap<>();
        mensajesEntrantesSinCorrelator = new HashMap<>();
    }

    @Override
    public void ejecuta() {
        for (Slot s : entradas) {
            relaciona(s.leerSlot());
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
            List<Mensaje> listMensaje = mensajesEntrantesCorelator.get(msg.getIdCorrelator());
            listMensaje.add(msg);
            if (mensajesEntrantesCorelator.get(msg.getIdCorrelator()).size() == salidas.size()) {
                escribirSalidas(listMensaje);
            }
        } else {
            List<Mensaje> nuevaLista = new ArrayList<>();
            nuevaLista.add(msg);
            mensajesEntrantesCorelator.put(msg.getIdCorrelator(), nuevaLista);
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
                List<Mensaje> listMensaje = mensajesEntrantesSinCorrelator.get(valor);
                listMensaje.add(msg);
                if (mensajesEntrantesSinCorrelator.get(valor).size() == salidas.size()) {
                    escribirSalidas(listMensaje);
                }
                //System.out.println("Pareja encontrada por valor: " + valor);
            } else {
                List<Mensaje> nuevaLista = new ArrayList<>();
                nuevaLista.add(msg);
                mensajesEntrantesSinCorrelator.put(valor, nuevaLista);
                //System.out.println("Mensaje guardado esperando pareja con valor: " + valor);
            }
        } catch (XPathExpressionException x) {
            System.err.println("Error en expresión XPath: " + xpathExpression);
            x.printStackTrace();
        }
    }

    private void escribirSalidas(List<Mensaje> listMensaje) {
        for (int i = 0; i < salidas.size(); i++) {
            salidas.get(i).escribirSlot(listMensaje.get(i));
        }
        mensajesEntrantesCorelator.remove(listMensaje.getFirst().getIdCorrelator());
    }
}
