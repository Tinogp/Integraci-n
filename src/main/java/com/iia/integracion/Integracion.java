package com.iia.integracion;

import com.iia.integracion.model.conector.ConectorBX;
import com.iia.integracion.model.conector.ConectorComanda;
import com.iia.integracion.model.mensaje.Mensaje;
import com.iia.integracion.model.puerto.PuertoEntrada;
import com.iia.integracion.model.puerto.PuertoSolicitud;
import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.Distributor;
import com.iia.integracion.tareas.Replicator;
import com.iia.integracion.tareas.Splitter;
import java.util.ArrayList;

public class Integracion {

    public static void main(String[] args) {
        // Declaracion de Slots
        Slot poll = new Slot();
        Slot splRep = new Slot();
        Slot repDisup = new Slot();
        Slot repDisdown = new Slot();
        Slot disTransUp = new Slot();
        Slot disTransDown = new Slot();
        Slot transbf = new Slot();
        Slot transbc = new Slot();
        Slot bfcorr = new Slot();
        Slot bccorr = new Slot();

        // Declaracion de puertos
        PuertoEntrada puertoe = new PuertoEntrada(poll);
        PuertoSolicitud puertosolbf = new PuertoSolicitud(transbf, bfcorr);
        PuertoSolicitud puertosolbc = new PuertoSolicitud(transbc, bccorr);

        // Declaracion de conectores
        ConectorComanda comandas = new ConectorComanda(puertoe, ".\\Comanda");
        ConectorBX bf = new ConectorBX(puertosolbf, "jdbc:mysql://tinovpn.duckdns.org:2025/bebidas");
        ConectorBX bc = new ConectorBX(puertosolbc, "jdbc:mysql://tinovpn.duckdns.org:2025/bebidas");

        // Declaracion de tareas
        ArrayList<Slot> apoll = new ArrayList<>();
        apoll.add(poll);
        ArrayList<Slot> aSplRep = new ArrayList<>();
        apoll.add(splRep);
        Splitter spl = new Splitter(apoll, aSplRep, "");

        ArrayList<Slot> aRepDis = new ArrayList<>();
        aRepDis.add(repDisup);
        aRepDis.add(repDisdown);
        Replicator rep = new Replicator(aSplRep, aRepDis);

        ArrayList<Slot> aDisTransUp = new ArrayList<>();
        aDisTransUp.add(disTransUp);
        ArrayList<Slot> aDisTransDown = new ArrayList<>();
        aDisTransDown.add(disTransDown);
        Distributor disUp = new Distributor(aRepDis.subList(0, 0), aDisTransUp, "");
        Distributor disDown = new Distributor(aRepDis.subList(1, 1), aDisTransDown, "");

    }

}
