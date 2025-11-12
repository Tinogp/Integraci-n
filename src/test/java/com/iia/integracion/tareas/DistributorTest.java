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
public class DistributorTest {
    
    private Slot entrada;
    private Slot salidaCaliente;
    private Slot salidaFria;
    
    private Distributor distributor;
    
    private Mensaje mensajeCaliente;
    private Mensaje mensajeFrio;
    
    private static final String XPATH_HOT = "drink/type = 'cold'";
    
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
        salidaCaliente = new Slot();
        salidaFria = new Slot();
        
        distributor = new Distributor(List.of(entrada), List.of(salidaCaliente, salidaFria), XPATH_HOT);
        
        Document docCaliente = crearDocumentoDesdeString("<drink><name>cafe</name><type>hot</type></drink>");
        mensajeCaliente = new Mensaje(docCaliente);

        Document docFrio = crearDocumentoDesdeString("<drink><name>coca-cola</name><type>cold</type></drink>");
        mensajeFrio = new Mensaje(docFrio);
    }
     
    @Test
    public void testBebidaCaliente() {
       
        entrada.escribirSlot(mensajeCaliente);
     
        distributor.ejecuta();               
        
        assertEquals(1, salidaCaliente.numMensajes());
        assertEquals(0, salidaFria.numMensajes());            
    }
    
    @Test
    public void testBebidaFria() {
       
        entrada.escribirSlot(mensajeFrio);
     
        distributor.ejecuta();               
        
        assertEquals(0, salidaCaliente.numMensajes());
        assertEquals(1, salidaFria.numMensajes());            
    }    
    
}