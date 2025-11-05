/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.iia.integracion.tareas;

import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.slot.Slot;
import java.util.ArrayList;
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
public class MergerTest {

    public MergerTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void mergerTest() {
        Slot entrada1 = new Slot();
        Slot entrada2 = new Slot();
        Slot entradaMerger1 = new Slot();
        Slot entradaMerger2 = new Slot();
        Slot salida = new Slot();
        Puerto puerto1 = new PuertoEntrada(entrada1);
        Puerto puerto2 = new PuertoEntrada(entrada2);
        ConectorComanda conector1 = new ConectorComanda(puerto1, "src/test/java/ficheroPrueba");
        ConectorComanda conector2 = new ConectorComanda(puerto2, "src/test/java/ficheroPrueba");
        conector1.ejecuta();
        conector2.ejecuta();

        Splitter splitter1 = new Splitter(List.of(entrada1), List.of(entradaMerger1), "/pedido/items/item");
        splitter1.ejecuta();
        Splitter splitter2 = new Splitter(List.of(entrada2), List.of(entradaMerger2), "/pedido/items/item");
        splitter2.ejecuta();

        List<Slot> entradasMerger = new ArrayList<>();
        entradasMerger.add(entradaMerger1);
        entradasMerger.add(entradaMerger2);
        Merger merger = new Merger(entradasMerger, List.of(salida));
        merger.ejecuta();
        
        assertEquals(6, salida.numMensajes());
    }
}
