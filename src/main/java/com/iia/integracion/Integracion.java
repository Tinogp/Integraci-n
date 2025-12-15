package com.iia.integracion;

import com.iia.integracion.model.GestorPrioridades;
import com.iia.integracion.model.conector.*;
import com.iia.integracion.model.puerto.*;
import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.*;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Integracion {

    public static void main(String[] args) {

        HashMap<Tarea, List<Slot>> mapa = new HashMap<Tarea, List<Slot>>();

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
        ConectorCamarero camarero = new ConectorCamarero(puertoSal, "entregas/camarero");

        // Declaracion de tareas
        Splitter spl = new Splitter(List.of(poll), List.of(splDis), "/cafe_order/drinks/drink");
        mapa.put(spl, List.of(poll));
        // IdSetter idSetter = new IdSetter(List.of(splDis), List.of(splDis));
        Distributor dis = new Distributor(List.of(splDis), List.of(disRepbf, disRepbc),
                List.of("drink/type = 'cold'", "drink/type = 'hot'"));
        mapa.put(dis, List.of(splDis));
        Replicator repbf = new Replicator(List.of(disRepbf), List.of(repTransbf, repCorrbf));
        mapa.put(repbf, List.of(disRepbf));
        Replicator repbc = new Replicator(List.of(disRepbc), List.of(repTransbc, repCorrbc));
        mapa.put(repbc, List.of(disRepbc));
        Translator transbf = new Translator(List.of(repTransbf), List.of(transPuerbf), "Translator/Traductorf.xsl"); // mirar
        mapa.put(transbf, List.of(repTransbf)); // archivo
        Translator transbc = new Translator(List.of(repTransbc), List.of(transPuerbc), "Translator/Traductorc.xsl");
        mapa.put(transbc, List.of(repTransbc));
        Correlator corrbf = new Correlator(List.of(repCorrbf, puerCorrbf), List.of(corrEnbf1, corrEnbf2), "//nombre");
        mapa.put(corrbf, List.of(repCorrbf, puerCorrbf));
        Correlator corrbc = new Correlator(List.of(repCorrbc, puerCorrbc), List.of(corrEnbc1, corrEnbc2), "//nombre");
        mapa.put(corrbc, List.of(repCorrbc, puerCorrbc));
        Enricher enbf = new Enricher(List.of(corrEnbf1, corrEnbf2), List.of(enMerbf));
        mapa.put(enbf, List.of(corrEnbf1, corrEnbf2));
        Enricher enbc = new Enricher(List.of(corrEnbc1, corrEnbc2), List.of(enMerbc));
        mapa.put(enbc, List.of(corrEnbc1, corrEnbc2));
        Merger mer = new Merger(List.of(enMerbf, enMerbc), List.of(merAgg));
        mapa.put(mer, List.of(enMerbf, enMerbc));
        Aggregator agg = new Aggregator(List.of(merAgg), List.of(salida), "//drinks");
        mapa.put(agg, List.of(merAgg));

        // Menú interactivo para ejecutar pasos
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nSeleccione una opción para ejecutar un paso:");
            System.out.println("1  - Ejecutar todo en secuencia");
            System.out.println("2  - Ejecutar todo en secuencia con IdSetter");
            System.out.println("3  - Ejecutar en paralelo");
            System.out.println("0  - Salir");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    // Ejecuta todo en el orden definido originalmente
                    comandas.ejecuta();
                    spl.ejecuta();
                    dis.ejecuta();
                    dis.ejecuta();
                    repbf.ejecuta();
                    repbc.ejecuta();
                    transbf.ejecuta();
                    transbc.ejecuta();
                    bf.ejecuta();
                    bc.ejecuta();
                    corrbf.ejecuta();
                    corrbc.ejecuta();
                    enbf.ejecuta();
                    enbc.ejecuta();
                    mer.ejecuta();
                    agg.ejecuta();
                    camarero.ejecuta();
                    break;
                case 2:
                    // Ejecuta todo en el orden definido originalmente con idSetter
                    comandas.ejecuta();
                    spl.ejecuta();
                    // idSetter.ejecuta();
                    dis.ejecuta();
                    dis.ejecuta();
                    repbf.ejecuta();
                    repbc.ejecuta();
                    transbf.ejecuta();
                    transbc.ejecuta();
                    bf.ejecuta();
                    bc.ejecuta();
                    corrbf.ejecuta();
                    corrbc.ejecuta();
                    enbf.ejecuta();
                    enbc.ejecuta();
                    mer.ejecuta();
                    agg.ejecuta();
                    camarero.ejecuta();
                    break;
                case 3:
                    // Ejecuta todo en paralelo
                    ExecutorService executor = Executors.newCachedThreadPool();
                    AtomicInteger valorMaximo = new AtomicInteger(0);
                    Tarea aEjecutar;
                    executor.execute(comandas);
                    executor.execute(bf);
                    executor.execute(bc);
                    executor.execute(camarero);
                    while (true) {
                        aEjecutar = GestorPrioridades.mayorPrioridad(mapa.keySet(), valorMaximo);
                        for (int i = valorMaximo.get() < 16 ? valorMaximo.get() : 16; i > 0; i--) {
                            executor.execute(aEjecutar);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();

    }

}
