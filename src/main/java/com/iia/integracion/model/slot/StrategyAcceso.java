package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.List;

/**
 * 
 * @author tinog
 */
public interface StrategyAcceso {
    Mensaje acceder(List<Mensaje> buffer);
}
