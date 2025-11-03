package com.iia.integracion.model.mensaje;

import java.io.StringWriter;
import java.util.UUID;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    public void setId(UUID id) {
        this.id = id;
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
    
    @Override
    public String toString() {
        String cuerpoStr;
        try {
            if (cuerpo != null) {
                // Convertir el Document a String legible
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                StringWriter writer = new StringWriter();
                transformer.transform(new DOMSource(cuerpo), new StreamResult(writer));
                cuerpoStr = writer.toString();
            } else {
                cuerpoStr = "null";
            }
        } catch (Exception e) {
            cuerpoStr = "[Error al convertir el cuerpo XML: " + e.getMessage() + "]";
        }

        return "Mensaje{"
                + "id=" + id
                + ", idCorrelator=" + idCorrelator
                + ", cuerpo=" + cuerpoStr
                + '}';
    }
}
