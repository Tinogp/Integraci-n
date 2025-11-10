package com.iia.integracion.model.puerto;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;

/**
 *
 * @author tinog
 */
public class PuertoSalida extends Puerto {

    public PuertoSalida(Slot slot) {
        super(slot);
    }

    public Mensaje ejecutaLectura() {
        return slot.leerSlot();
    }
}
