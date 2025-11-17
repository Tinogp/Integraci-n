package com.iia.integracion.tareas;

import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.puerto.Puerto;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.slot.Slot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnricherTest {

    @Test
    public void testEnricher() {
        Slot entrada = new Slot();
        Slot salida = new Slot();
        Puerto puerto = new PuertoEntrada(entrada);
        ConectorComanda conector = new ConectorComanda(puerto, "src/test/java/ficheroPrueba");
        conector.ejecuta();

        //Enricher enricher = new Enricher(List.of(entrada), List.of(salida), "/pedido/codigoPedido", "prueba");
        //enricher.ejecuta();

        assertEquals(1, salida.numMensajes());
    }

}
