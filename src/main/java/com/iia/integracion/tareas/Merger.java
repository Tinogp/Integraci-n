package com.iia.integracion.tareas;

import com.iia.integracion.model.mensaje.Mensaje;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author tinog
 */
public class Merger {

    /*Hemos pensado que esta clase tenga un atributo privado que sea un map donde
    la clave sea el UUID del mensaje original y el valor sea una lista de los 
    mensajes fragmentados que va recibiendo 
     */
    private Map<UUID, List<Mensaje>> comandas
            = new HashMap<UUID, List<Mensaje>>();
}
