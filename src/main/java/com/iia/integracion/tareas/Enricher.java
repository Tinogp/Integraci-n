package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 *
 * @author Alvaro
 */
public class Enricher extends Tarea {

    private String etiquetaFuente;
    private String etiquetaDestino;

    public Enricher(List<Slot> entradas, List<Slot> salidas, String etiquetaFuente, String equitaDestino) {
        super(entradas, salidas);
        this.etiquetaFuente = etiquetaFuente;
        this.etiquetaDestino = equitaDestino;
    }

    @Override
    public void ejecuta() {


        Mensaje msgFuente = entradas.get(0).leerSlot();

        Mensaje msgDestino = entradas.get(1).leerSlot();               

        if(msgFuente != null && msgDestino != null){
           
            String valor = extraerValorPorEtiqueta(msgFuente.getCuerpo(), etiquetaFuente);

            if(valor != null){
                inyectarValor(msgDestino.getCuerpo(), etiquetaDestino, valor);
            } else {
                System.out.println("Etiqueta fuente no encontrada: " + etiquetaFuente);
            }

            salidas.get(0).escribirSlot(new Mensaje(msgDestino));        

        }
        
    }

    /*
     * Método auxiliar para leer el valor de una etiqueta específica del documento XML.
     */
    private String extraerValorPorEtiqueta(Document doc, String etiqueta) {

        Element raiz = doc.getDocumentElement();
        NodeList nodos = raiz.getElementsByTagName(etiqueta);

        if (nodos.getLength() > 0) { // Si se encuentra la etiqueta
            return nodos.item(0).getTextContent();
        }

        return null;
    }

    /*
     * Inyecta un valor en una etiqueta específica del documento XML.
     */
    private void inyectarValor(Document doc, String etiqueta, String valor) {

        Element raiz = doc.getDocumentElement();
        NodeList nodos = raiz.getElementsByTagName(etiqueta);

        if (nodos.getLength() > 0) { // Si se encuentra la etiqueta
            nodos.item(0).setTextContent(valor);

        } else { //si no existe, crear etiqueta y añadirla al final
            Element nuevoElemento = doc.createElement(etiqueta);
            nuevoElemento.setTextContent(valor);
            raiz.appendChild(nuevoElemento);            
        }
    }

    
}
