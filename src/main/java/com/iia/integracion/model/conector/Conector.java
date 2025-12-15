package com.iia.integracion.model.conector;

import com.iia.integracion.model.puerto.Puerto;

public abstract class Conector implements Runnable {

    protected Puerto puerto;
    protected String Polling;

    public Conector(Puerto puerto, String Polling) {
        this.puerto = puerto;
        this.Polling = Polling;
    }

    public abstract void ejecuta();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ejecuta();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
