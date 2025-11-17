package com.iia.integracion.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.w3c.dom.Document;

/**
 *
 * @author tinog
 */
public class ComandasSingleton {

    /**
     * UUID: id del mensaje original 
     */
    public static Map<UUID, Document> comandas = new HashMap<UUID, Document>();
    public final static ComandasSingleton singleton = new ComandasSingleton();

    public static Map<UUID, Document> getInstancia() {
        return comandas;
    }     

    /**
     * Este método sirve para relacionar el id de un mensaje con el order id
     *
     * @param id id del mensaje original
     * @return devolvemos si se ha añadido de forma correcta o no al Map
     */
    public static boolean addMensaje(UUID id, Document order) {       
        return comandas.putIfAbsent(id, order) == null;
    }
    
    /**
     * @param id del mensaje original
     * @return devuelve el order id según un id de mensaje
     */
    public static Document getOrder(UUID id){
        return comandas.get(id);
    }
}