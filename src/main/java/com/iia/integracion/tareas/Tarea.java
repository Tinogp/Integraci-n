package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 * 
 * @author Quique
 */
public abstract class Tarea implements Runnable {
    protected List<Slot> entradas;
    protected List<Slot> salidas;

    // Este método abstracto contendrá la lógica de PROCESAR UN SOLO MENSAJE
    //public abstract void procesarMensaje(Mensaje msg);
    
    public Tarea(List<Slot> entradas, List<Slot> salidas) {
        this.entradas = entradas;
        this.salidas = salidas;
    }

    @Override
    public void run() {
        // Bucle infinito: la tarea siempre está viva esperando mensajes
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // leerSlot() ahora espera pasivamente si no hay nada
                Mensaje msg = entradas.get(0).leerSlot(); 
                
                if (msg != null) {
                    //procesarMensaje(msg);
                }
            }
        } catch (Exception e) {
            System.err.println("Error en la ejecución de la tarea: " + e.getMessage());
        }
    }    

    /* 
    public abstract void ejecuta();

    public List<Slot> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<Slot> entradas) {
        this.entradas = entradas;
    }

    public List<Slot> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<Slot> salidas) {
        this.salidas = salidas;
    }*/
    
}