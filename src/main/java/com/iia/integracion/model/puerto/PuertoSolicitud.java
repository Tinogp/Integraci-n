package com.iia.integracion.model.puerto;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;

/** 
 *
 *@author tinog
 */
public class PuertoSolicitud extends Puerto {

    private Slot slotSalida;

    public PuertoSolicitud(Slot slotEntrada, Slot slotSalida) {
        super(slotEntrada);
        this.slotSalida = slotSalida;
    }

    public void ejecutaEscritura(Mensaje mensaje) {
        slotSalida.escribirSlot(mensaje);
    }

    public Mensaje ejecutaLectura() {
        return slot.leerSlot();
    }

}
