package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.List;
import org.w3c.dom.Document;

/**
 * 
 * @author tinog
 */
public interface StrategyAcceso {
    Mensaje acceder(List<Mensaje> buffer);
}
