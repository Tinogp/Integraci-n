package com.iia.integracion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.iia.integracion.model.concurrencia.gestorPrioridades;
import com.iia.integracion.model.conector.ConectorBX;
import com.iia.integracion.model.conector.ConectorCamarero;
import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.puerto.PuertoSalida;
import com.iia.integracion.model.puerto.PuertoSolicitud;
import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.Aggregator;
import com.iia.integracion.tareas.Correlator;
import com.iia.integracion.tareas.Distributor;
import com.iia.integracion.tareas.Enricher;
import com.iia.integracion.tareas.IdSetter;
import com.iia.integracion.tareas.Merger;
import com.iia.integracion.tareas.Replicator;
import com.iia.integracion.tareas.Splitter;
import com.iia.integracion.tareas.Tarea;
import com.iia.integracion.tareas.Translator;


public class cafeConcurrente {

    public static void main(String[] args) {
        // Declaracion de Slots
        Slot poll = new Slot();
        Slot splDis = new Slot();
        Slot disRepbf = new Slot();
        Slot disRepbc = new Slot();
        Slot repTransbf = new Slot();
        Slot repTransbc = new Slot();
        Slot repCorrbf = new Slot();
        Slot repCorrbc = new Slot();
        Slot transPuerbf = new Slot();
        Slot transPuerbc = new Slot();
        Slot puerCorrbf = new Slot();
        Slot puerCorrbc = new Slot();
        Slot corrEnbf1 = new Slot();
        Slot corrEnbf2 = new Slot();
        Slot corrEnbc1 = new Slot();
        Slot corrEnbc2 = new Slot();
        Slot enMerbf = new Slot();
        Slot enMerbc = new Slot();
        Slot merAgg = new Slot();
        Slot salida = new Slot();

        // Declaracion de puertos
        PuertoEntrada puertoe = new PuertoEntrada(poll);
        PuertoSolicitud puertoSolbf = new PuertoSolicitud(transPuerbf, puerCorrbf);
        PuertoSolicitud puertoSolbc = new PuertoSolicitud(transPuerbc, puerCorrbc);
        PuertoSalida puertoSal = new PuertoSalida(salida);

        // Declaracion de conectores
        ConectorComanda comandas = new ConectorComanda(puertoe, ".\\Comandas");
        ConectorBX bf = new ConectorBX(puertoSolbf, "jdbc:mysql://tinovpn.duckdns.org:2025/bebidas");
        ConectorBX bc = new ConectorBX(puertoSolbc, "jdbc:mysql://tinovpn.duckdns.org:2025/bebidas");
        ConectorCamarero camarero = new ConectorCamarero(puertoSal, "entregas/camarero.xml");

        // Declaracion de tareas
        Splitter spl = new Splitter(List.of(poll), List.of(splDis), "/cafe_order/drinks/drink");
        IdSetter idSetter = new IdSetter(List.of(splDis), List.of(splDis));
        Distributor dis = new Distributor(List.of(splDis), List.of(disRepbf, disRepbc),
                List.of("drink/type = 'cold'", "drink/type = 'hot'"));
        Replicator repbf = new Replicator(List.of(disRepbf), List.of(repTransbf, repCorrbf));
        Replicator repbc = new Replicator(List.of(disRepbc), List.of(repTransbc, repCorrbc));
        Translator transbf = new Translator(List.of(repTransbf), List.of(transPuerbf), "Translator/Traductorf.xsl"); // mirar
                                                                                                                     // archivo
        Translator transbc = new Translator(List.of(repTransbc), List.of(transPuerbc), "Translator/Traductorc.xsl");
        Correlator corrbf = new Correlator(List.of(repCorrbf, puerCorrbf), List.of(corrEnbf1, corrEnbf2), "//nombre");
        Correlator corrbc = new Correlator(List.of(repCorrbc, puerCorrbc), List.of(corrEnbc1, corrEnbc2), "//nombre");
        Enricher enbf = new Enricher(List.of(corrEnbf1, corrEnbf2), List.of(enMerbf));
        Enricher enbc = new Enricher(List.of(corrEnbc1, corrEnbc2), List.of(enMerbc));
        Merger mer = new Merger(List.of(enMerbf, enMerbc), List.of(merAgg));
        Aggregator agg = new Aggregator(List.of(merAgg), List.of(salida), "//drinks");

        // Ejecuta concurrentemente
        ExecutorService poolConectores = Executors.newCachedThreadPool();
        poolConectores.submit(comandas);
        poolConectores.submit(bf);
        poolConectores.submit(bc);
        poolConectores.submit(camarero);

        int numHilosTareas = 4;
        ExecutorService poolTareas = Executors.newFixedThreadPool(numHilosTareas);
        List<Tarea> tareas = List.of(spl, dis, dis, repbf, repbc, transbf, transbc,
                corrbf, corrbc, enbf, enbc, mer, agg);

        while (true) {
            try {
                // CACULAR PRIORIDADES
                gestorPrioridades gestorPrioridades = new gestorPrioridades();
                gestorPrioridades.setPolitica(new com.iia.integracion.model.concurrencia.mayorEntrada());
                gestorPrioridades.asignarPrioridad(tareas);
                tareas.sort((tp1, tp2) -> Integer.compare(tp2.getPrioridad(), tp1.getPrioridad()));

                // 4. ENVIAR AL POOL Y GUARDAR EL "RECIBO" (FUTURE)
                for (int i = 0; i < numHilosTareas; i++) {
                    Tarea t = tareas.get(i);
                    poolTareas.submit(t);
                }
                // BLOQUEAR HASTA QUE TERMINEN
                for (int i = 0; i < numHilosTareas; i++) {

                }
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}