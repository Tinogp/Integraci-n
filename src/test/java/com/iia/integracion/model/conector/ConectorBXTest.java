/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.iia.integracion.model.conector;

import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.slot.Slot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author tinog
 */
public class ConectorBXTest {
    
    public ConectorBXTest() {
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

    /**
     * Test of ejecuta method, of class ConectorBX.
     */
    @Test
    public void testEjecuta() {
        System.out.println("ejecuta");
        ConectorBX instance = new ConectorBX(new PuertoEntrada(new Slot()), "");
        instance.ejecuta();
        assertTrue(true);
    }
    
}
