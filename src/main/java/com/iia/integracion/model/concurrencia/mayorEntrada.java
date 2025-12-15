package com.iia.integracion.model.concurrencia;

import java.util.List;

import com.iia.integracion.tareas.Tarea;

public class mayorEntrada implements politicaPrioridad {
    @Override
    public void asignarPrioridades(List<Tarea> tareas) {
        for (Tarea t : tareas) {
            int prioridad = 0;
            for (var entrada : t.getEntradas()) {
                prioridad += entrada.numMensajes();
            }
            t.setPrioridad(prioridad);
        }
    }
}
