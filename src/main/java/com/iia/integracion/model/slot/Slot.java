package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;

/**
 * @author tinog
 */
public class Slot {
    
    private final List<Mensaje> buff = new ArrayList<>();
    private StrategyAcceso estrategiaAcceso;
    
    public Slot(){
        estrategiaAcceso = new StrategyLeerPrimero();
    }
    
    public Slot(StrategyAcceso estrategiaInicial) {
        this.estrategiaAcceso = estrategiaInicial;
    }
    
    public void setStrategy(StrategyAcceso nuevaEstrategia) {
        System.out.println("\nCambio de estrategia\n");
        this.estrategiaAcceso = nuevaEstrategia;
    }
    
    public void escribirSlot(Mensaje mensaje){
        buff.add(mensaje);
    }
    
    public Mensaje leerSlot(){
        return estrategiaAcceso.acceder(buff);
    }
    
    
}
