package com.iia.integracion.model;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.Aggregator;
import com.iia.integracion.tareas.Correlator;
import com.iia.integracion.tareas.Distributor;
import com.iia.integracion.tareas.Enricher;
import com.iia.integracion.tareas.Merger;
import com.iia.integracion.tareas.Replicator;
import com.iia.integracion.tareas.Splitter;
import com.iia.integracion.tareas.Tarea;
import com.iia.integracion.tareas.Translator;

public class GestorPrioridades {

    // La politica pensada para la ejecucion de los hilos irá en función de la
    // cantidad de mensajes que tengan las tareas en sus
    // slots de entrada. Hay tareas que a pesar de tener muchos mensajes en un slot,
    // si otro de ellos esta vacío
    // no podrá entrar en funcionamiento, por lo que hay que tener en cuenta no solo
    // la cantidad de mensajes que
    // tenemos en los slots de entrada, sino que también si todos los slots de
    // entrada están ocupados o no.

    /**
     * 
     * @param tareas
     * @param valorMax
     * @return
     */
    public static Tarea mayorPrioridad(Set<Tarea> tareas, AtomicInteger valorMaximo) {
        int valorMax = 0;
        int actual = 0;
        int min = Integer.MAX_VALUE;
        int valorAcum = 0;
        Tarea aEjecutar = null;
        for (Tarea t : tareas) {
            switch (t) {
                case Splitter spliter -> {
                    actual = t.getEntradas().getFirst().numMensajes();
                    if (actual > valorMax) {
                        valorMax = actual;
                        aEjecutar = spliter;
                    }
                }
                case Distributor distributor -> {
                    actual = t.getEntradas().getFirst().numMensajes();
                    if (actual > valorMax) {
                        valorMax = actual;
                        aEjecutar = distributor;
                    }
                }
                case Replicator replicator -> {
                    actual = t.getEntradas().getFirst().numMensajes();
                    if (actual > valorMax) {
                        valorMax = actual;
                        aEjecutar = replicator;
                    }
                }
                case Translator translator -> {
                    actual = t.getEntradas().getFirst().numMensajes();
                    if (actual > valorMax) {
                        valorMax = actual;
                        aEjecutar = translator;
                    }
                }
                case Correlator correlator -> {
                    min = Integer.MAX_VALUE;
                    for (Slot s : t.getEntradas())
                        if (s.numMensajes() < min)
                            min = s.numMensajes();
                    if (min > valorMax) {
                        valorMax = min;
                        aEjecutar = correlator;
                    }
                }
                case Enricher enricher -> {
                    min = Integer.MAX_VALUE;
                    for (Slot s : t.getEntradas())
                        if (s.numMensajes() < min)
                            min = s.numMensajes();
                    if (min > valorMax) {
                        valorMax = min;
                        aEjecutar = enricher;
                    }
                }
                case Merger merger -> {
                    valorAcum = 0;
                    for (Slot s : t.getEntradas())
                        valorAcum += s.numMensajes();
                    if ((valorAcum / t.getEntradas().size()) > valorMax) {
                        valorMax = Math.ceilDiv(valorAcum, t.getEntradas().size());
                        aEjecutar = merger;
                    }
                }
                case Aggregator aggregator -> {
                    actual = t.getEntradas().getFirst().numMensajes();
                    if (actual > valorMax) {
                        valorMax = actual;
                        aEjecutar = aggregator;
                    }
                }
                default -> {
                    System.err.println("Error: Tarea no reconocida");
                }
            }
        }
        valorMaximo.set(valorMax);
        return aEjecutar;
    }
}
