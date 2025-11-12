package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author Quique
 */
public class Replicator extends Tarea {

    /**
     *
     * @param entradas lista de slots de entrada (1)
     * @param salidas lista de slots de salida (2 o mas)
     */
    public Replicator(List<Slot> entradas, List<Slot> salidas) {

        super(entradas, salidas);

        //Comprobar numeros de slots para el replicator
        if (entradas == null || entradas.size() != 1) {
            throw new IllegalArgumentException("Replicator debe tener 1 slot de entrada");
        }

        if (salidas == null || salidas.size() < 2) {
            throw new IllegalArgumentException("Replicator debe tener al menos 2 slots de salida");
        }
    }

    @Override
    public void ejecuta() {

        Mensaje mensajeOriginal = this.entradas.getFirst().leerSlot();

        if (mensajeOriginal != null) {

            Slot salida1 = this.salidas.get(0);
            Slot salida2 = this.salidas.get(1);

            Document cuerpoOriginal = mensajeOriginal.getCuerpo();

            //Copia 1
            Mensaje mensaje1 = new Mensaje(cuerpoOriginal);
            mensaje1.setIdCorrelator(mensajeOriginal.getId());

            //Copia 2
            Mensaje mensaje2 = new Mensaje(cuerpoOriginal);
            mensaje2.setIdCorrelator(mensajeOriginal.getId());

            //Escribir copias en slots de salidas
            salida1.escribirSlot(mensaje1);
            salida2.escribirSlot(mensaje2);
        }

    }

}
