package com.iia.integracion;

import com.iia.integracion.model.conector.*;
import com.iia.integracion.model.puerto.*;
import com.iia.integracion.model.slot.Slot;
import com.iia.integracion.tareas.*;
import java.util.List;

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
        ConectorCamarero camarero = new ConectorCamarero(puertoSal, "/entregas/camarero.xml");


        // Declaracion de tareas
        Splitter spl = new Splitter(List.of(poll), List.of(splDis), "/cafe_order/drinks/drink");            
        Distributor dis = new Distributor(List.of(splDis), List.of(disRepbf, disRepbc), List.of("drink/type = 'cold'", "drink/type = 'hot'"));        
        Replicator repbf = new Replicator(List.of(disRepbf), List.of(repTransbf, repCorrbf));
        Replicator repbc = new Replicator(List.of(disRepbc), List.of(repTransbc, repCorrbc));
        Translator transbf = new Translator(List.of(repTransbf), List.of(transPuerbf), "/frio.xsl"); //mirar archivo
        Translator transbc = new Translator(List.of(repTransbc), List.of(transPuerbc), "/caliente.xsl");
        Correlator corrbf = new Correlator(List.of(repCorrbf, puerCorrbf), List.of(corrEnbf1, corrEnbf2), "/drink/id");
        Correlator corrbc = new Correlator(List.of(repCorrbc, puerCorrbc), List.of(corrEnbc1, corrEnbc2), "/drink/id");
        Enricher enbf = new Enricher(List.of(corrEnbf1, corrEnbf2), List.of(enMerbf));
        Enricher enbc = new Enricher(List.of(corrEnbc1, corrEnbc2), List.of(enMerbc));
        Merger mer = new Merger(List.of(enMerbf, enMerbc), List.of(merAgg));
        Aggregator agg = new Aggregator(List.of(merAgg), List.of(salida), "cafe_order");

        // Ejecucion del sistema de integracion
        comandas.ejecuta();
        spl.ejecuta();
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
    }

}
