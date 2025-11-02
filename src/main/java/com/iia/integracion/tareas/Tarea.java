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
    protected String xpathExpression;
    
    // Constructor Base (para subclases que usan xpathExpression)
    public Tarea(List<Slot> entradas, List<Slot> salidas, String xpath) {
        this.entradas = entradas;
        this.salidas = salidas;
        this.xpathExpression = xpath;
    }

    // Constructor Alternativo (para subclases que NO usan xpathExpression)
    public Tarea(List<Slot> entradas, List<Slot> salidas) {
        this.entradas = entradas;
        this.salidas = salidas;
        this.xpathExpression = null;
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

    public String getXpathExpression() {
        return xpathExpression;
    }

    public void setXpathExpression(String xpathExpression) {
        this.xpathExpression = xpathExpression;
    }            
}