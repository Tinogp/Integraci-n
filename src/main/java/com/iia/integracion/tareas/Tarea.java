package com.iia.integracion.tareas;

import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 *
 * @author tinog
 */
public abstract class Tarea {

    private  List<Slot> entradas;
    private List<Slot> salidas;
    private String xPathEspression;

    public abstract void ejecuta();
}