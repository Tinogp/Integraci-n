/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.iia.integracion.tareas;

import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.slot.Slot;
import java.util.List;
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
public class SplitterTest {

    public SplitterTest() {
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
    public void testSplitter() {
        Slot entrada = new Slot();
        Slot salida = new Slot();
        Puerto puerto = new PuertoEntrada(entrada);
        ConectorComanda conector = new ConectorComanda(puerto, "src/test/java/ficheroPrueba");
        conector.ejecuta();

        Splitter splitter = new Splitter(List.of(entrada), List.of(salida), "/pedido/items/item");
        splitter.ejecuta();

        assertEquals(3, salida.numMensajes());
    }
}