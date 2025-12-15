package com.iia.integracion.model.concurrencia;
import java.util.List;
import com.iia.integracion.tareas.Tarea;

public class gestorPrioridades {
    private politicaPrioridad politica;
    
    public void setPolitica(politicaPrioridad politica) {
        this.politica = politica;
    }

    public void asignarPrioridad(List<Tarea> tareas) {
        politica.asignarPrioridades(tareas);
    }
    
}
