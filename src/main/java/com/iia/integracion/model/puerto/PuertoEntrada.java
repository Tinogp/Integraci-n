package com.iia.integracion.model.puerto;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;

public class PuertoEntrada extends Puerto {

    public PuertoEntrada(Slot slot) {
        super(slot);
    }


    //traducir de otros formatos como json a xml
    public void ejecutaEscritura(Mensaje mensaje) {
        slot.escribirSlot(mensaje);
    }

}
