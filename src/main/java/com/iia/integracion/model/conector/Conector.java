package com.iia.integracion.model.conector;

import com.iia.integracion.model.puerto.Puerto;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

public abstract class Conector {

    protected Puerto puerto;
    protected String directorioPolling;

    public Conector(Puerto puerto, String directorioPolling) {
        this.puerto = puerto;
        this.directorioPolling = directorioPolling;
    }
    
    
    public abstract void ejecuta();
}
