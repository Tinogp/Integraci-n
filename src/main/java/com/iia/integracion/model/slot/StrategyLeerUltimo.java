package com.iia.integracion.model.slot;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.List;
import org.w3c.dom.Document;

/**
 * 
 * @author tinog
 */
public class StrategyLeerUltimo implements StrategyAcceso{

    @Override
    public Mensaje acceder(List<Mensaje> buffer) {
        if (buffer.isEmpty()) {
            System.out.println("Buffer vacio...");
            return null;
        }
        Mensaje mensaje = buffer.getLast();
        buffer.removeLast();
        return mensaje;
    }
    
}
