package com.iia.integracion.model.mensaje;

import java.util.UUID;
import org.w3c.dom.Document;

public class MensajeFragmento extends Mensaje{
    
    private UUID idFragment;

    public MensajeFragmento(Document cuerpo) {
        super(cuerpo);
        this.idFragment = UUID.randomUUID();
    }

    public UUID getIdFragment() {
        return idFragment;
    }

    public void setIdFragment(UUID idFragment) {
        this.idFragment = idFragment;
    }
    
    
}
