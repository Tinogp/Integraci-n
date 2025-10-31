package com.iia.integracion.model;

import com.iia.integracion.model.mensaje.MensajeFragmento;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 *
 * @author tinog
 */
public class ComandasSingleton {

    /**
     * UUID: id del mensaje original Integer: cantidad de fragmentos que hay en
     * la lista, es decir, que actualmente conforman el mensaje original
     */
    public static Map<UUID, List<UUID>> comandas = new HashMap<UUID, List<UUID>>();

    public static Map<UUID, List<UUID>> getInstancia() {
        return comandas;
    }

    /**
     * Este método sirve para añadir un nuevo mensaje original al Map
     *
     * @param id id del mensaje original
     * @return devolvemos si se ha añadido de forma correcta o no al Map
     */
    public static boolean addMensaje(UUID id) {
        List<UUID> listaVacia = new ArrayList<>();
        return comandas.putIfAbsent(id, listaVacia) == null;
    }

    public static boolean addMensajeFragmento(UUID id, UUID idFragmento) {
        return comandas.get(id).add(idFragmento);
    }
}
