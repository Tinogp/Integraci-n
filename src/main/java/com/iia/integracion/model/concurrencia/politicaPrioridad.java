package com.iia.integracion.model.concurrencia;

import java.util.List;
import com.iia.integracion.tareas.Tarea;

public interface politicaPrioridad {
    public void asignarPrioridades(List<Tarea> tareas);
}
