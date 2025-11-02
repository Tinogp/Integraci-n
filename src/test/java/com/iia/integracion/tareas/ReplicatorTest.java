/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.slot.Slot;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Enrique
 */
public class ReplicatorTest {
    
    private Slot entrada;
    private Slot salida1;
    private Slot salida2;
    
    private Replicator replicator;
    
    private Mensaje mensaje;         
    
    private Document crearDocumentoDesdeString(String xmlString) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));
            return (Document) builder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("Error al parsear XML de prueba", e);
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        entrada = new Slot();
        salida1 = new Slot();
        salida2 = new Slot();
        
        replicator = new Replicator(List.of(entrada), List.of(salida1, salida2));
        
        Document docCaliente = crearDocumentoDesdeString("<drink><name>cafe</name><type>hot</type></drink>");
        mensaje = new Mensaje(docCaliente);     
    }
     
    @Test
    public void testEjecuta() {
       
        entrada.escribirSlot(mensaje);
     
        replicator.ejecuta();               
        
        assertEquals(1, salida1.numMensajes());
        assertEquals(1, salida2.numMensajes());  
        
        //Comprobar contenido de las salidas
    }
    
    
    
}