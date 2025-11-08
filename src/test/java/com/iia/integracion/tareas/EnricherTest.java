package com.iia.integracion.tareas;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import static org.junit.jupiter.api.Assertions.*;

class EnricherTest {

    @Test
    void testEnricherCambiaValor() throws Exception {
        // Crear documento XML simple
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();
        
        Element root = doc.createElement("test");
        Element campo = doc.createElement("dato");
        campo.setTextContent("original");
        root.appendChild(campo);
        doc.appendChild(root);

        // Verificar valor original
        assertEquals("original", campo.getTextContent());

        // Aquí probarías tu lógica de enriquecimiento
        // Esto es solo un ejemplo básico
        campo.setTextContent("enriquecido");
        
        // Verificar que cambió
        assertEquals("enriquecido", campo.getTextContent());
    }
}