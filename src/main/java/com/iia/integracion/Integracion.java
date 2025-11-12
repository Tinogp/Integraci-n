package com.iia.integracion;

import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.Replicator;
import com.iia.integracion.tareas.Splitter;
import java.util.ArrayList;

public class Integracion {

    public static void main(String[] args) {
        //Declaracion de Slots
        Slot poll = new Slot();
        Slot splRep = new Slot();
        Slot repDis1 = new Slot();
        Slot repDis2 = new Slot();

        //Declaracion de puertos
        PuertoEntrada puertoe = new PuertoEntrada(poll);

        //Declaracion de conectores
        ConectorComanda comandas = new ConectorComanda(puertoe, ".\\Comanda");

        //Declaracion de tareas
        ArrayList<Slot> apoll = new ArrayList<>();
        apoll.add(poll);
        ArrayList<Slot> aSplRep = new ArrayList<>();
        apoll.add(splRep);
        Splitter spl = new Splitter(apoll, aSplRep, "");

        ArrayList<Slot> aRepDis = new ArrayList<>();
        aRepDis.add(repDis1);
        aRepDis.add(repDis2);
        Replicator rep = new Replicator(aSplRep, aRepDis);
        

    }
}
