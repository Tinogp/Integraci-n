package com.iia.integracion.model.mensaje;

import java.util.UUID;
import org.w3c.dom.Document;

public class Mensaje {

    protected UUID id;
    protected UUID idCorrelator;
    protected Document cuerpo;

    public Mensaje(Document cuerpo) {
        this.id = UUID.randomUUID();
        this.idCorrelator = null;
        this.cuerpo = cuerpo;
    }

    public UUID getId() {
        return id;
    }

    public UUID getIdCorrelator() {
        return idCorrelator;
    }

    public void setIdCorrelator(UUID idCorrelator) {
        this.idCorrelator = idCorrelator;
    }

    public Document getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Document cuerpo) {
        this.cuerpo = cuerpo;
    }
    
    
}
