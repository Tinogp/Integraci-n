package com.iia.integracion.model.puerto;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.*;

public abstract class Puerto {

    protected Slot slot;

    public Puerto(Slot slot) {
        this.slot = slot;
    }

    public abstract void ejecutaEscritura(Mensaje mensaje);
    public abstract Mensaje ejecutaLectura();
}
