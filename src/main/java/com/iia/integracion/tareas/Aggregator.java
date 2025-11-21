package com.iia.integracion.tareas;

import com.iia.integracion.model.ComandasSingleton;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Quique
 */
public class Aggregator extends Tarea {

    private Map<UUID, List<Mensaje>> mapaFragmentos; // asocia los fragmentos al mensaje original

    private String xpathExpression;

    public Aggregator(List<Slot> entradas, List<Slot> salidas, String xpath) {
        super(entradas, salidas);
        this.xpathExpression = xpath;

        this.mapaFragmentos = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {

            Mensaje msg = entradas.getFirst().leerSlot();

            if() msg == null) {
                break;
            }

            UUID idMsg = msg.getId();

            // Almacenar el fragmento
            List<Mensaje> listaFrag = mapaFragmentos.computeIfAbsent(idMsg, k -> new ArrayList<>());
            listaFrag.add(msg);

            if (listaFrag.size() == msg.getTamano()) {

                Document infoCompleta = unirFragmentos(listaFrag, this.xpathExpression);

                Mensaje msgFinal = new Mensaje(infoCompleta, idMsg);

                salidas.getFirst().escribirSlot(msgFinal);

                mapaFragmentos.remove(idMsg);
            }
        }
    }

    /**
     * Crea un nuevo documento XML con los fragmentos de la lista.
     *
     * @param fragmentos Lista de fragmentos
     * @param xpath      Nombre del elemento raíz del nuevo documento.
     * @return El Documento XML completo
     */
    private Document unirFragmentos(List<Mensaje> fragmentos, String xpath) {
        // Introducir fragmentos en el documento original
        Document docOriginal = ComandasSingleton.getOrder(fragmentos.get(0).getId());
        // moverse al nodo donde se van a insertar los fragmentos a partir del
        // xpathExpression
        XPath xPath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
        Node NodoBuscado = null;
        try {
            NodoBuscado = (Node) xPath.evaluate(xpath, docOriginal, javax.xml.xpath.XPathConstants.NODE);
        } catch (Exception ex) {
            Logger.getLogger(Aggregator.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        for (Mensaje frag : fragmentos) {
            Node nodoFrag = docOriginal.importNode(frag.getCuerpo().getDocumentElement(), true);
            NodoBuscado.appendChild(nodoFrag);
        }
        return docOriginal;
    }
}