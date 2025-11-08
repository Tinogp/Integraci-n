package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author Alvaro
 */
public class Enricher extends Tarea {

    //Este atributo es el valor que se añadira.
    private String valor;

    public Enricher(List<Slot> entradas, List<Slot> salidas, String xpath, String valor) {
        super(entradas, salidas, xpath);
        this.valor = valor;
    }

    @Override
    public void ejecuta() {
        Mensaje msg = entradas.getFirst().leerSlot();
        Document cuerpoMensaje = msg.getCuerpo();

        //añadir informacion mediante expresion xpath con valor
        
        Mensaje msgFinal= new Mensaje(cuerpoMensaje);
        salidas.getFirst().escribirSlot(msgFinal);      
    }
}
