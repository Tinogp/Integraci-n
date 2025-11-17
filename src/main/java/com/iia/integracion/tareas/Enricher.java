package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alvaro
 */
public class Enricher extends Tarea {

    public Enricher(List<Slot> entradas, List<Slot> salidas) {
        super(entradas, salidas);
    }

    @Override
    public void ejecuta() {
        Document mensajeBase = entradas.get(0).leerSlot().getCuerpo();
        Document mensajeEnriquecimiento = entradas.get(1).leerSlot().getCuerpo();

        fusionarNodos(mensajeBase.getDocumentElement(), mensajeEnriquecimiento.getDocumentElement());

        Mensaje mensajeSalida = new Mensaje(mensajeBase);
        //System.out.println(mensajeSalida);
        salidas.getFirst().escribirSlot(mensajeSalida);
    }

    private void fusionarNodos(Element nodoBase, Element nodoEnriquecimiento) {
        NodeList hijosEnriquecimiento = nodoEnriquecimiento.getChildNodes();

        for (int i = 0; i < hijosEnriquecimiento.getLength(); i++) {
            Node hijoEnriquecimiento = hijosEnriquecimiento.item(i);

            if (hijoEnriquecimiento.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoEnriquecimiento = (Element) hijoEnriquecimiento;
                String nombreElemento = elementoEnriquecimiento.getTagName();

                // Buscar si existe un elemento con el mismo nombre en el nodo base
                NodeList hijosBase = nodoBase.getChildNodes();
                boolean encontrado = false;

                for (int j = 0; j < hijosBase.getLength(); j++) {
                    Node hijoBase = hijosBase.item(j);
                    if (hijoBase.getNodeType() == Node.ELEMENT_NODE
                            && ((Element) hijoBase).getTagName().equals(nombreElemento)) {

                        // Si ambos tienen hijos, fusionar recursivamente
                        if (elementoEnriquecimiento.hasChildNodes() && hijoBase.hasChildNodes()) {
                            fusionarNodos((Element) hijoBase, elementoEnriquecimiento);
                        } else {
                            // Si el elemento de enriquecimiento tiene contenido, reemplazar
                            if (elementoEnriquecimiento.hasChildNodes()
                                    || elementoEnriquecimiento.getTextContent() != null) {
                                nodoBase.replaceChild(
                                        nodoBase.getOwnerDocument().importNode(elementoEnriquecimiento, true),
                                        hijoBase
                                );
                            }
                        }
                        encontrado = true;
                        break;
                    }
                }
                // Si no se encontró el elemento en el base, agregarlo
                if (!encontrado) {
                    Node nodoImportado = nodoBase.getOwnerDocument().importNode(elementoEnriquecimiento, true);
                    nodoBase.appendChild(nodoImportado);
                }
            }
        }
    }
}