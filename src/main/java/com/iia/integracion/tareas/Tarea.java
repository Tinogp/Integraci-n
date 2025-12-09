package com.iia.integracion.tareas;

import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 * 
 * @author Quique
 */
public abstract class Tarea implements Runnable {
    protected List<Slot> entradas;
    protected List<Slot> salidas;
    String nombreTarea;

    public Tarea(List<Slot> entradas, List<Slot> salidas, String nombreTarea) {
        this.entradas = entradas;
        this.salidas = salidas;
        this.nombreTarea = nombreTarea;
    }

    public abstract void ejecuta();

    @Override
    public void run() {
        System.out.println("Iniciando hilo para tarea: " + nombreTarea);
        // Bucle infinito: el hilo vive mientras la aplicación corra
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ejecuta();
            } catch (Exception e) {
                System.err.println("Error procesando mensaje en " + nombreTarea + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

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
    }

}