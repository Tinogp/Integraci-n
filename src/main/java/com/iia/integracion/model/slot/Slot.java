package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author tinog
 */
public class Slot {

    private final BlockingQueue<Mensaje> buff = new LinkedBlockingQueue<>();   
    //private StrategyAcceso estrategiaAcceso;
    public Slot() {
        //estrategiaAcceso = new StrategyLeerPrimero();
    }

    /* 
    public Slot(StrategyAcceso estrategiaInicial) {
        this.estrategiaAcceso = estrategiaInicial;
    }

    public void setStrategy(StrategyAcceso nuevaEstrategia) {
        System.out.println("\nCambio de estrategia\n");
        this.estrategiaAcceso = nuevaEstrategia;
    }
    */

    public void escribirSlot(Mensaje mensaje) {
        try {
            // put se bloquea si la cola estuviera llena (opcional si limitamos tamaño)
            buff.put(mensaje); 
            System.out.println("Mensaje escrito en slot. Total: " + buff.size());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido al escribir");
        }
    }

    public Mensaje leerSlot() {
        try {
            // take() BLOQUEA el hilo si no hay mensajes, esperando a que llegue uno
            return buff.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public int numMensajes() {
        return buff.size();
    }
}
