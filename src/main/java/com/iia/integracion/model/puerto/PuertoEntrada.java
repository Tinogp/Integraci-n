package com.iia.integracion.model.puerto;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;

public class PuertoEntrada extends Puerto{

    public PuertoEntrada(Slot slot) {
        super(slot);
    }

    @Override
    public void ejecuta(Mensaje mensaje) {
        slot.escribirSlot(mensaje);
    }

    
    
}
