package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author tinog
 */
public class StrategyLeerPrimero implements StrategyAcceso {

    @Override
    public Mensaje acceder(BlockingQueue<Mensaje> buffer) {
        if (buffer.isEmpty()) {
            return null;
        }
        Mensaje mensaje;
        try {
            mensaje = buffer.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido al leer primero");
            return null;
        }
        return mensaje;
    }

}
