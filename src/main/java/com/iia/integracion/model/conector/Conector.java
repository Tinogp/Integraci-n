package com.iia.integracion.model.conector;

import com.iia.integracion.model.puerto.Puerto;

public abstract class Conector {

    protected Puerto puerto;
    protected String Polling;

    public Conector(Puerto puerto, String Polling) {
        this.puerto = puerto;
        this.Polling = Polling;
    }

    public abstract void ejecuta();
}
