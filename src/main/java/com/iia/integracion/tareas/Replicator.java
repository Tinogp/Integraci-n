package com.iia.integracion.tareas;

import com.iia.integracion.model.slot.Slot;
import java.util.List;

/**
 * 
 * @author Quique
 */
public class Replicator extends Tarea{

    public Replicator(List<Slot> entradas, List<Slot> salidas) {
        super(entradas, salidas);
    }

    @Override
    public void ejecuta() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}