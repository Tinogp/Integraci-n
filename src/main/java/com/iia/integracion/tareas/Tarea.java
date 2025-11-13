package com.iia.integracion.tareas;

import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 * 
 * @author Quique
 */
public abstract class Tarea {
    protected List<Slot> entradas;
    protected List<Slot> salidas;
    
    public Tarea(List<Slot> entradas, List<Slot> salidas) {
        this.entradas = entradas;
        this.salidas = salidas;
    }

   
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
    }
    
}