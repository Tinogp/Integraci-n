package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Quique
 */
public class Aggregator extends Tarea {

    private Map<UUID, List<Mensaje>> mapaFragmentos; //asocia los fragmentos al mensaje original   

    public Aggregator(List<Slot> entradas, List<Slot> salidas, String xpath) {
        super(entradas, salidas, xpath);

        this.mapaFragmentos = new HashMap<>();
    }

    @Override
    public void ejecuta() {
        
        Mensaje msg = entradas.getFirst().leerSlot();

        UUID idMsg = msg.getId();

        // Almacenar el fragmento
        List<Mensaje> listaFrag = mapaFragmentos.computeIfAbsent(idMsg, k -> new ArrayList<>());
        listaFrag.add(msg);

        if (listaFrag.size() == msg.getTamano()) {            
            
            Document infoCompleta = unirFragmentos(listaFrag, this.xpathExpression, idMsg);

            Mensaje msgFinal = new Mensaje(infoCompleta, idMsg);

            salidas.getFirst().escribirSlot(msgFinal);

            mapaFragmentos.remove(idMsg);
        }
    }

    /**
     * Crea un nuevo documento XML con los fragmentos de la lista.
     *
     * @param fragmentos Lista de fragmentos
     * @param xpath Nombre del elemento raíz del nuevo documento.
     * @return El Documento XML completo
     */
    private Document unirFragmentos(List<Mensaje> fragmentos, String raíz, UUID id) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document nuevoDoc = builder.newDocument();
            Node raiz = nuevoDoc.createElement(raíz);
            nuevoDoc.appendChild(raiz);

            Node orderId = nuevoDoc.createElement("order_id");
            orderId.setTextContent(id.toString());
            raiz.appendChild(orderId);

            Node drinks = nuevoDoc.createElement("drinks");
            raiz.appendChild(drinks);

            for (Mensaje fragmento : fragmentos) {
                Node raizFrag = fragmento.getCuerpo().getDocumentElement();
                Node nodoimport = nuevoDoc.importNode(raizFrag, true);
                drinks.appendChild(nodoimport);
            }

            return nuevoDoc;

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Aggregator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
