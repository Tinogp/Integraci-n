package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tinog
 */
public class Slot {

    private final List<Mensaje> buff = new ArrayList<>();
    private StrategyAcceso estrategiaAcceso;

    public Slot() {
        estrategiaAcceso = new StrategyLeerPrimero();
    }

    public Slot(StrategyAcceso estrategiaInicial) {
        this.estrategiaAcceso = estrategiaInicial;
    }

    public void setStrategy(StrategyAcceso nuevaEstrategia) {
        System.out.println("\nCambio de estrategia, de " + estrategiaAcceso + " a " + nuevaEstrategia + "\n");
        this.estrategiaAcceso = nuevaEstrategia;
    }

    public void escribirSlot(Mensaje mensaje) {
        System.out.println("Se esta escribiendo el mensaje: " + mensaje);
        buff.add(mensaje);
    }

    public Mensaje leerSlot() {
        Mensaje mensaje = estrategiaAcceso.acceder(buff);
        System.out.println("Se esta leyendo el mensaje: " + mensaje);
        return mensaje;
    }

    public void eliminarListaMensajes(List<Mensaje> listaMensajes) {
        for (Mensaje m : listaMensajes) {
            buff.remove(m);
        }
    }

    public int numMensajes() {
        return buff.size();
    }

    public List<Mensaje> getBuff() {
        if(estrategiaAcceso instanceof StrategyCopiaBuff) {
            return ((StrategyCopiaBuff) estrategiaAcceso).copiarBuff(buff);
        } else {
            return new ArrayList<>(buff);
        }
    }
}
