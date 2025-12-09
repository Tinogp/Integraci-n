package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;

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

        super(entradas, salidas, "Replicator");

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
            
            for (int i = 0; i < salidas.size(); i++) {
                
                Slot salida = this.salidas.get(i);
                
                Mensaje mensaje = new Mensaje(mensajeOriginal);
                
                salida.escribirSlot(mensaje);
                
            }           
        }

    }

}
