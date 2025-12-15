package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author tinog
 */
public interface StrategyAcceso {
    Mensaje acceder(BlockingQueue<Mensaje> buffer);
}
