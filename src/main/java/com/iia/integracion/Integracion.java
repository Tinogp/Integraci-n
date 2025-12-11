package com.iia.integracion;

import com.iia.integracion.model.conector.*;
import com.iia.integracion.model.puerto.*;
import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Integracion {

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

        // Ejecucion del sistema de integracion
        /**
         * comandas.ejecuta();
         * spl.ejecuta();
         * dis.ejecuta();
         * repbf.ejecuta();
         * repbc.ejecuta();
         * transbf.ejecuta();
         * transbc.ejecuta();
         * bf.ejecuta();
         * bc.ejecuta();
         * corrbf.ejecuta();
         * corrbc.ejecuta();
         * enbf.ejecuta();
         * enbc.ejecuta();
         * mer.ejecuta();
         * agg.ejecuta();
         * camarero.ejecuta();
         **/

        // Menú interactivo para ejecutar pasos
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\nSeleccione una opción para ejecutar un paso:");
            System.out.println(" 1  - Conector Comandas");
            System.out.println(" 2  - Splitter");
            System.out.println(" 3  - Distributor");
            System.out.println(" 4  - Replicator BF");
            System.out.println(" 5  - Replicator BC");
            System.out.println(" 6  - Translator BF");
            System.out.println(" 7  - Translator BC");
            System.out.println(" 8  - Conector BF (BD)");
            System.out.println(" 9  - Conector BC (BD)");
            System.out.println("10  - Correlator BF");
            System.out.println("11  - Correlator BC");
            System.out.println("12  - Enricher BF");
            System.out.println("13  - Enricher BC");
            System.out.println("14  - Merger");
            System.out.println("15  - Aggregator");
            System.out.println("16  - Conector Camarero");
            System.out.println("17  - Ejecutar todo en secuencia");
            System.out.println("18  - Ejecutar todo en secuencia con IdSetter");
            System.out.println("19  - Ejecutar de forma concurrente");
            System.out.println(" 0  - Salir");
            System.out.print("Opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Intente de nuevo.");
                continue;
            }

            switch (opcion) {
                case 1:
                    comandas.ejecuta();
                    break;
                case 2:
                    spl.ejecuta();
                    break;
                case 3:
                    dis.ejecuta();
                    dis.ejecuta();
                    break;
                case 4:
                    repbf.ejecuta();
                    break;
                case 5:
                    repbc.ejecuta();
                    break;
                case 6:
                    transbf.ejecuta();
                    break;
                case 7:
                    transbc.ejecuta();
                    break;
                case 8:
                    bf.ejecuta();
                    break;
                case 9:
                    bc.ejecuta();
                    break;
                case 10:
                    corrbf.ejecuta();
                    break;
                case 11:
                    corrbc.ejecuta();
                    break;
                case 12:
                    enbf.ejecuta();
                    break;
                case 13:
                    enbc.ejecuta();
                    break;
                case 14:
                    mer.ejecuta();
                    break;
                case 15:
                    agg.ejecuta();
                    break;
                case 16:
                    camarero.ejecuta();
                    break;
                case 17:
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
                case 18:
                    // Ejecuta todo en el orden definido originalmente con idSetter
                    comandas.ejecuta();
                    spl.ejecuta();
                    idSetter.ejecuta();
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
                case 19:
                    //Ejecuta concurrentemente
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
                            List<TareaPrioridad> colaPrioridad = new ArrayList<>();

                            for (Tarea t : tareas) {
                                int prioridad = calcularPrioridad(t);
                                if (prioridad > 0) {
                                    colaPrioridad.add(new TareaPrioridad(t, prioridad));
                                }
                            }

                            // ORDENAR COLA POR PRIORIDAD DE MAYOR A MENOR
                            colaPrioridad.sort((tp1, tp2) -> Integer.compare(tp2.prioridad, tp1.prioridad));

                           int tareasALanzar = Math.min(colaPrioridad.size(), numHilosTareas);

                            if (tareasALanzar > 0) {
                                List<Future<?>> tareasEnEjecucion = new ArrayList<>();

                                // 4. ENVIAR AL POOL Y GUARDAR EL "RECIBO" (FUTURE)
                                for (int i = 0; i < tareasALanzar; i++) {
                                    TareaPrioridad tp = colaPrioridad.get(i);
                                    // .submit() devuelve un Future que representa la ejecución pendiente
                                    Future<?> futuro = poolTareas.submit(tp.tarea);
                                    tareasEnEjecucion.add(futuro);
                                }

                                // BLOQUEAR HASTA QUE TERMINEN
                                for (Future<?> f : tareasEnEjecucion) {
                                    try {                                        
                                        f.get(); // se detiene este bucle while hasta que la tarea termina
                                    } catch (ExecutionException e) {
                                        e.printStackTrace();
                                    }
                                }
                                        
                            } else {
                                // Si no hay nada que hacer, dormir para no quemar CPU
                                Thread.sleep(100);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
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

        // La politica pensada para la ejecucion de los hilos irá en función de la
        // cantidad de mensajes que tengan las tareas en sus
        // slots de entrada. Hay tareas que a pesar de tener muchos mensajes en un slot,
        // si otro de ellos esta vacío
        // no podrá entrar en funcionamiento, por lo que hay que tener en cuenta no solo
        // la cantidad de mensajes que
        // tenemos en los slots de entrada, sino que también si todos los slots de
        // entrada están ocupados o no.

    }

    private static int calcularPrioridad(Tarea t) {
        List<Slot> entradas = t.getEntradas();

        // Para enricher y correlator sumamos todos los mensajes de todas las entradas,
        // si alguno está vacío, no se ejecuta
        if (t instanceof Enricher || t instanceof Correlator) {
            int totalMensajes = 0;
            for (Slot s : entradas) {
                int n = s.numMensajes();
                if (n == 0)
                    return 0;
                totalMensajes += n;
            }
            return totalMensajes + 100; // sumamos 100 para darle prioridad
        }

        // Sumamos todos los mensajes de todas las entradas
        int totalMensajes = 0;
        for (Slot s : entradas) {
            totalMensajes += s.numMensajes();
        }

        // Para meger, se hace media del numero de mensajes con el numero de entradas
        if (t instanceof Merger) {
            return totalMensajes / entradas.size();
        }

        return totalMensajes;
    }

}

class TareaPrioridad {
    Tarea tarea;
    int prioridad;

    public TareaPrioridad(Tarea t, int p) {
        this.tarea = t;
        this.prioridad = p;
    }
}
