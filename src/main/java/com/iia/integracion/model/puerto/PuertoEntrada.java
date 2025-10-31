package com.iia.integracion.model.puerto;

import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import org.w3c.dom.Document;

public class PuertoEntrada extends Puerto{

    public PuertoEntrada(Slot slot) {
        super(slot);
    }

    @Override
    public void ejecuta(Mensaje mensaje) {
        slot.escribirSlot(mensaje);
    }

    
    
}
