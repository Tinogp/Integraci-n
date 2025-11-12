package com.iia.integracion.model.conector;

import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.puerto.PuertoSalida;
import com.iia.integracion.model.slot.Slot;
import java.io.File;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author tinog
 */
public class ConectorCamareroTest {

    private static final String RUTA_BASE = "src/test/java/com/iia/integracion/model/conector/Pruebas/";

    public ConectorCamareroTest() {
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
     * Test of ejecuta method, of class ConectorCamarero.
     */
    @Test
    public void testEjecuta() {
        System.out.println("ejecuta");
        Slot s = new Slot();
        Puerto pe = new PuertoEntrada(s);
        Puerto ps = new PuertoSalida(s);
        String st = "src\\test\\java\\com\\iia\\integracion\\model\\conector\\Pruebas";
        ConectorComanda conectorcomanda = new ConectorComanda(pe, st);
        ConectorCamarero conectrocamarero = new ConectorCamarero(ps, st.concat("\\SALIDA.xml"));
        conectorcomanda.ejecuta();
        conectrocamarero.ejecuta();
        // TODO review the generated test code and remove the default call to fail.
        File fe = new File("src\\test\\java\\com\\iia\\integracion\\model\\conector\\Pruebas\\comanda1.xml");
        File fs = new File("src\\test\\java\\com\\iia\\integracion\\model\\conector\\Pruebas\\SALIDA.xml");
        String en = null, sa = null;
        try {
            en = Files.readString(fe.toPath(), StandardCharsets.UTF_8).trim().strip().stripIndent().stripLeading().stripTrailing();
            sa = Files.readString(fs.toPath(), StandardCharsets.UTF_8).trim().strip().stripIndent().stripLeading().stripTrailing();
            System.out.println(en);
            System.out.println(sa);
        } catch (IOException ex) {
            System.getLogger(ConectorCamareroTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        assertTrue(en.equals(sa));
    }

}
