package com.iia.integracion.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author tinog
 */
public class ComandasSingleton {

    /**
     * UUID: id del mensaje original 
     */
    public static Map<UUID, String> comandas = new HashMap<UUID, String>();
    public final static ComandasSingleton singleton = new ComandasSingleton();

    public static Map<UUID, String> getInstancia() {
        return comandas;
    }     

    /**
     * Este método sirve para relacionar el id de un mensaje con el order id
     *
     * @param id id del mensaje original
     * @return devolvemos si se ha añadido de forma correcta o no al Map
     */
    public static boolean addMensaje(UUID id, String order) {       
        return comandas.putIfAbsent(id, order) == null;
    }
    
    /**
     * 
     * @param id del mensaje original
     * @return devuelve el order id según un id de mensaje
     */
    public static String getOrder(UUID id){
        return comandas.get(id);
    }
}