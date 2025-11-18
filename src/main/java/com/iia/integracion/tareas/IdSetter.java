package com.iia.integracion.tareas;

import java.util.List;
import java.util.UUID;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;

public class IdSetter extends Tarea {
    
    public IdSetter(List<Slot> entradas, List<Slot> salidas) {
        super(entradas, salidas);
    }

    @Override
    public void ejecuta() {
        for (int i = 0; i < entradas.get(0).numMensajes(); i++) {
            Mensaje msg = entradas.get(0).leerSlot();
            msg.setIdCorrelator(UUID.randomUUID());
            salidas.get(0).escribirSlot(msg);
        }
    }

}
