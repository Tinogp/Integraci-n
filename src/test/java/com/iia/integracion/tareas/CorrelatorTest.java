/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.iia.integracion.tareas;

import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author 34646
 */
public class CorrelatorTest {

    public CorrelatorTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testSinCorrelator() {
        Slot entrada = new Slot();
        //Slot salidaSplitter = new Slot();
        Slot entradaCorrelator = new Slot();
        Slot salida1 = new Slot();
        Slot salida2 = new Slot();

        Puerto puerto = new PuertoEntrada(entrada);
        ConectorComanda conector = new ConectorComanda(puerto, "src/test/java/ficheroPrueba");
        conector.ejecuta();
        //conector.ejecuta();

        Splitter splitter = new Splitter(List.of(entrada), List.of(entradaCorrelator), "//items/item/producto");
        splitter.ejecuta();

        
        Correlator correlator = new Correlator(List.of(entradaCorrelator, entradaCorrelator), List.of(salida1, salida2), "//producto");
        correlator.ejecuta();

        assertEquals(1, salida1.numMensajes());
        assertEquals(1, salida2.numMensajes());
    }

    @Test
    public void testConCorrelator() {
        Slot entrada = new Slot();
        Slot salidaSplitter = new Slot();
        Slot entradaCorrelator = new Slot();
        Slot salida1 = new Slot();
        Slot salida2 = new Slot();

        Puerto puerto = new PuertoEntrada(entrada);
        ConectorComanda conector = new ConectorComanda(puerto, "src/test/java/ficheroPrueba");
        conector.ejecuta();
        //conector.ejecuta();

        Splitter splitter = new Splitter(List.of(entrada), List.of(salidaSplitter), "//items/item/producto");
        splitter.ejecuta();

        UUID uuid = UUID.randomUUID();
        while (salidaSplitter.numMensajes() > 0) {
            Mensaje m = salidaSplitter.leerSlot();
            m.setIdCorrelator(uuid);
            entradaCorrelator.escribirSlot(m);
        }

        Correlator correlator = new Correlator(List.of(entradaCorrelator, entradaCorrelator), List.of(salida1, salida2), "//producto");
        correlator.ejecuta();

        assertEquals(1, salida1.numMensajes());
        assertEquals(1, salida2.numMensajes());
    }
}
