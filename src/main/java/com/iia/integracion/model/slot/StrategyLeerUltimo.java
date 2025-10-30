package com.iia.integracion.model.slot;

import java.util.List;
import org.w3c.dom.Document;

/**
 * 
 * @author tinog
 */
public class StrategyLeerUltimo implements StrategyAcceso{

    @Override
    public Document acceder(List<Document> buffer) {
        if (buffer.isEmpty()) {
            System.out.println("Buffer vacio...");
            return null;
        }
        Document doc = buffer.getLast();
        buffer.removeLast();
        return doc;
    }
    
}
