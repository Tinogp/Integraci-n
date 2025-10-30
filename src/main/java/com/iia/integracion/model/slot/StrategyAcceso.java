package com.iia.integracion.model.slot;

import java.util.List;
import org.w3c.dom.Document;

/**
 * 
 * @author tinog
 */
public interface StrategyAcceso {
    Document acceder(List<Document> buffer);
}
