package com.iia.integracion.tareas;

import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 *
 * @author tinog
 */
public abstract class Tarea {

    protected List<Slot> entradas;
    protected List<Slot> salidas;
    protected String xPathEspression;

    public Tarea(List<Slot> entradas, List<Slot> salidas, String xPathEspression) {
        this.entradas = entradas;
        this.salidas = salidas;
        this.xPathEspression = xPathEspression;
    }

    public abstract void ejecuta();
}
