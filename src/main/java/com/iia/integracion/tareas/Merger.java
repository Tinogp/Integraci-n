package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 *
 * @author Alvaro
 */
public class Merger extends Tarea {

    public Merger(List<Slot> entradas, List<Slot> salidas) {
        super(entradas, salidas, "Merger");
    }

    @Override
    public void ejecuta() {
        for (Slot slot : entradas) {
            Mensaje mensaje = slot.leerSlot();
            if (mensaje != null) {
                salidas.getFirst().escribirSlot(mensaje);
            }
        }
    }

}
